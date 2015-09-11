package database;

import interfaces.ISaveAndDelete;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import utils.HibernateSupport;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Location implements ISaveAndDelete {

	@Id
	protected int id;
	
	@OneToMany
	@JoinColumn(name="location")
	protected List<ProductElement> product_elements;
	
	public Location() {
		this.product_elements = new ArrayList<ProductElement>();		
	}
	
	public int getId() {
		return id;
	}

	public List<ProductElement> getProductElements() {
		return product_elements;
	}
	
	protected int getNextId() {
		Criteria c = HibernateSupport.getCurrentSession().createCriteria(Location.class);
		c.addOrder(Order.desc("id"));
		c.setMaxResults(1);
		Location location = (Location)c.uniqueResult();
		
		int id = 0;
		if(location != null) {
			id = location.getId();
		}
		return id + 1;
	}
	
	public boolean addProductElement(ProductElement elem) {
		boolean success = false;
		
		if(!this.product_elements.contains(elem)) {
			if (this.product_elements.add(elem)){
				success = elem.saveToDB();
				this.saveToDB();
			} else {
				return false;
			}
		}
		
		return success;
	}
	
	public boolean otherLocationContainsElement(List<Location> locations, ProductElement elem) {
		
		Location location;
		List<ProductElement> elements;
		
		int locations_size = locations.size();
		for(int i = 0; i < locations_size; i++) {
			location = locations.get(i);
			if(location.getId() == this.getId()) {
				System.out.println("otherLocationContainsElement EQUALS");
				continue;
			}
			
			elements = location.getProductElements();
			for(int j = 0; j < elements.size(); j++) {
				if(elem.getId() == elements.get(j).getId()) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public boolean otherLocationContainsElement(ProductElement elem) {
		
		List<Location> locations = HibernateSupport.readMoreObjects(Location.class, new ArrayList<Criterion>());
		Location location;
		List<ProductElement> elements;
		
		int locations_size = locations.size();
		for(int i = 0; i < locations_size; i++) {
			location = locations.get(i);
			if(location.getId() == this.getId()) {
				System.out.println("otherLocationContainsElement EQUALS");
				continue;
			}
			
			elements = location.getProductElements();
			for(int j = 0; j < elements.size(); j++) {
				if(elem.getId() == elements.get(j).getId()) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductElement> getNumberOfProductElementsFromProductCategory(int number, Product product) {
		
		if(this.product_elements.size() < number) {
			return null;
		}

		List<ProductElement> elements;
		List<ProductElement> result = new ArrayList<ProductElement>();
		
		HibernateSupport.beginTransaction();
		Criteria c = HibernateSupport.getCurrentSession().createCriteria(ProductElement.class);
		c.add(Restrictions.eq("location", this));
		c.add(Restrictions.eq("product", product));
		elements = c.list();
		HibernateSupport.commitTransaction();
		
		if(elements.size() < number) {
			System.out.println("getNumberOfProductElementsFromProductCategory elements.size < number");
			return null;
		}
		
		for(int i = 0; i < number; i++) {
			result.add(elements.get(i));
		}
		
		System.out.println(elements.size());
		System.out.println(result.size());

		
		return result;
	}
	
	public boolean removeProductElement(ProductElement elem) {
		
		boolean success = false;
		for(int i = 0; i < this.product_elements.size(); i++) {
			if(this.product_elements.get(i).getId() == elem.getId()) {
				this.product_elements.remove(i);
				success = true;
			}

		}
		
		return success;
	}
	
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#serialize()
	 */
	@Override
	public String serialize() {
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
