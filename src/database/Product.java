package database;

import java.util.ArrayList;
import java.util.List;

import interfaces.ISaveAndDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import utils.HibernateSupport;

@Entity
public class Product implements ISaveAndDelete {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	private String name;

	private String description;
		
	private int minimum_limit;
	
	private String unity;

	
	@OneToMany
	@JoinColumn(name="product")
	private List<ProductElement> product_elements;
	
	public Product() {
		this.product_elements = new ArrayList<ProductElement>();
	}
	
	public Product(String serialized_product) {
		String[] tmp = serialized_product.split("\t");
		this.name = tmp[0];
		this.description = tmp[1];
		this.unity = tmp[2];
		this.minimum_limit = Integer.parseInt(tmp[3]);
	}
	
	public Product(String name, String description, int minimum_limit) {
		this.product_elements = new ArrayList<ProductElement>();
		this.name = name;
		this.description = description;
		this.minimum_limit = minimum_limit;
	}
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getMinimumLimit() {
		return minimum_limit;
	}
	
	public boolean addProductElement(ProductElement elem) {
		boolean success = false;
		
		if(!this.product_elements.contains(elem)) {
			if (this.product_elements.add(elem)){
				success = elem.saveToDB();
			} else {
				return false;
			}
		}
		
		return success;
	}
	
	public static Product createProduct(String name, String description, int minimum_limit) {		

		// Check, if product already exists
		Product product = Product.getProduct(name, description);
		
		// product exists and can't be created again
		if (product != null) {
			return null;
		}
		
		// create a new product and set it's parameter
		Product new_product = new Product(name, description, minimum_limit);
		
		// Store the created product in the DB and return it's object, in case of a successful writing.
		HibernateSupport.beginTransaction();
			boolean success = new_product.saveToDB();
		HibernateSupport.commitTransaction();
		
		if (success) {
			return new_product;
		} else {
			return null;
		}
	}
	
	public static Product getProduct(String name){
		
		Product product = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		product =  HibernateSupport.readOneObject(Product.class, criterions);
		return product;

	}
	
	public static Product getProduct(String name, String description){
		
		Product product = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		criterions.add(Restrictions.eq("description", description));
		product =  HibernateSupport.readOneObject(Product.class, criterions);
		return product;

	}
	
	public static Product getProduct(int id){
		return HibernateSupport.readOneObjectByID(Product.class, id);
	}

	@Override
	public String serialize() {
		return this.name + "\t" + this.description + "\t" +  this.unity + "\t" + this.minimum_limit;
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
