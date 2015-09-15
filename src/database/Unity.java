package database;

import interfaces.ISaveAndDelete;

import java.util.ArrayList;
import java.util.List;

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
public class Unity implements ISaveAndDelete {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(name="unity")
	private List<Product> products;
	
	public Unity() {}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Unity(String name) {
		this.products = new ArrayList<Product>();
		this.name = name;
	}
	
	public static Unity getUnity(String name) {
		Unity unity;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		unity =  HibernateSupport.readOneObject(Unity.class, criterions);

		if(unity == null) {
			unity = new Unity(name);
			HibernateSupport.beginTransaction();
			
			HibernateSupport.commitTransaction();
		}
		
		return unity;
	}
	
	public boolean addProduct(Product product) {
		boolean success = false;
		if(product != null)  {
			success = this.products.add(product);
		}
		
		return success;
	}

	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#saveToDB()
	 */
	@Override
	public boolean saveToDB() {
		return HibernateSupport.commit(this);
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#deleteFromDB(java.lang.Object)
	 */
	@Override
	public void deleteFromDB(Object obj) {
		HibernateSupport.deleteObject(this);	
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
