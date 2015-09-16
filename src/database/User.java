package database;

import interfaces.ISaveAndDelete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;

import database.Product.ProductState;
import utils.Email;
import utils.HibernateSupport;

@Entity
public class User implements ISaveAndDelete {
	
	public enum Permission {
		ADMIN, USER
	}

	@Id
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
		
	private String password_hash;
	
	@Enumerated(value=EnumType.ORDINAL)
	protected Permission permission;
	
	@OneToMany
	@JoinColumn(name="transaction")
	private List<Transaction> transactions;
	
	public User() {}
	
	public User(String username, String firstname, String lastname, String email, 
			String password_hash, Permission permission) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password_hash = password_hash;
		this.permission = permission;
	}
	
	public static User register(String username, String firstname, String lastname, 
								String email, String password_plain, Permission permission) {
		// Check, if user already exists
		User user = getUser(username);
		
		if (user != null)
			return null; // user exists and can't be created again
		
		// hash the password
		String salt = BCrypt.gensalt();
		String password_hash = BCrypt.hashpw(password_plain, salt);
		
		// create a new user and set it's parameter
		User new_user = new User(username, firstname, lastname, email, password_hash, permission);
		
		// Store the created user in the DB and return it's object, in case of a successful writing.
		HibernateSupport.beginTransaction();
		boolean success = new_user.saveToDB();
		HibernateSupport.commitTransaction();
		
		if (success) {
			return new_user;
		} else {
			return null;
		}
	}
	
	public static User login(String username, String password_plaintext){
		
		// Check, if user exists
		User user = getUser(username);

		if (user != null && BCrypt.checkpw(password_plaintext, user.getPasswordHash())) {
			return user;
		} else {
			return null;
		}

	}
	
	private static User getUser(String username){
		
		User user = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("username", username));
		user =  HibernateSupport.readOneObject(User.class, criterions);
		return user;

	}
	
	public boolean moveNumberOfProductElements(int quantity, Product product, Location dest, Location src) {
		
		List<ProductElement> elements = new ArrayList<ProductElement>();
		ProductElement elem;
		
		//This is the inventory for newly created ProductElements
		if(src.getId() == 1) {
			HibernateSupport.beginTransaction();

				for(int i = 0; i < quantity; i++) {
					elem = new ProductElement();
					src.addProductElement(elem);
					product.addProductElement(elem);
				}
				product.setState(ProductState.ACTIVE);
				src.saveToDB();
				product.saveToDB();
			HibernateSupport.commitTransaction();
		}
		
		elements = src.getNumberOfProductElementsFromProductCategory(quantity, product);
		
		if(elements == null) {
			System.out.println("moveNumberOfProductElements elements == NULL");
			return false;
		}
		
		boolean success = processTransaction(elements, dest, src);
		
		//This is the main inventory
		if(src.getId() == 2) {
			long current_quantity = src.getQuantityOfSpecificProduct(product.getId());
			if(current_quantity <= product.getMinimumLimit()) {
				System.out.println("I will send an e-mail to all admins.");
				Email.sendQuantityReminder(product, current_quantity);
			}
		}
		
		return success;
	}
	
	private boolean processTransaction(List<ProductElement> elements, Location dest, Location src) {
		Date date = new Date();
		
		List<Location> locations = HibernateSupport.readMoreObjects(Location.class, 
													new ArrayList<Criterion>());
		
		int elements_size = elements.size();
		ProductElement elem;
		for(int i = 0; i < elements.size(); i++) {
			elem = elements.get(i);
			if(src.otherLocationContainsElement(locations, elem)) {
				return false;
			}
		}
		
		HibernateSupport.beginTransaction();
		for(int i = 0; i < elements_size; i++) {
			elem = elements.get(i);
			
			System.out.println(src.product_elements.size());
			if(!src.removeProductElement(elem)) {
				System.out.println("moving elem failed: could not remove from src...");
				return false;
			}
			
			if(!dest.product_elements.add(elem)) {
				src.product_elements.add(elem);
				System.out.println("moving elem failed: could not add to dest...");
				return false;
			}
						
		}
		

		this.addTransaction(new Transaction(src, dest, this, date, elements));
		src.saveToDB();
		dest.saveToDB();
		HibernateSupport.commitTransaction();
		
		return true;
	}
	
	private boolean addTransaction(Transaction transaction) {
		boolean success = false;
		
		if (this.transactions.add(transaction)){
			success = transaction.saveToDB();
			this.saveToDB();
		} else {
			return false;
		}
		
		return success;
	}
	
	public String getUsername() {
		return username;
	}


	public String getFirstname() {
		return firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public String getEmail() {
		return email;
	}


	public String getPasswordHash() {
		return password_hash;
	}


	public Permission getPermission() {
		return permission;
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#saveToDB()
	 */
	@Override
	public boolean saveToDB() {
		if(!HibernateSupport.commit(this))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#deleteFromDB(java.lang.Object)
	 */
	@Override
	public void deleteFromDB(Object obj) {
		HibernateSupport.deleteObject(this);	
	}
	
	
}
