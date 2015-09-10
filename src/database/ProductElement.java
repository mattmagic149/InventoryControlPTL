package database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import utils.HibernateSupport;
import interfaces.ISaveAndDelete;

@Entity
public class ProductElement implements ISaveAndDelete  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="product",updatable=false) ///TODO: nullable?!?!
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="location",updatable=true)
	private Location location;


	public ProductElement() {}

	public int getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public Location getLocation() {
		return location;
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
