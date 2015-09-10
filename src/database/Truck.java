
package database;

import java.util.ArrayList;
import java.util.List;

import interfaces.*;

import javax.persistence.*;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import utils.*;

/**
 * The Class Truck.
 */
@Entity
public class Truck extends Location implements ISaveAndDelete {
	
	private String licence_tag;
	
<<<<<<< HEAD
	@ManyToMany(mappedBy="trucks_to_restrict")
	private List<Product> products_consumeable;
	
=======
>>>>>>> d981bac4b448cd0cfa9b9e6e8174335eec67bbaf
	public Truck() {
		super();
	}
	
	public Truck(String licence_tag) {
		this.licence_tag = licence_tag;
	}
	
		
	public String getLicenceTag() {
		return licence_tag;
	}
	
	public static Truck createTruck(String licence_tag){		

		// Check, if truck already exists
		Truck truck = Truck.getTruck(licence_tag);
		
		// truck exists and can't be created again
		if (truck != null) {
			return null;
		}
		
		// create a new truck and set it's parameter
		Truck new_truck = new Truck(licence_tag);
		
		// Store the created truck in the DB and return it's object, in case of a successful writing.
		HibernateSupport.beginTransaction();
			boolean success = new_truck.saveToDB();
		HibernateSupport.commitTransaction();
		
		if (success) {
			return new_truck;
		} else {
			return null;
		}
	}
	
	public static Truck getTruck(String licence_tag){
		
		Truck truck = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("licence_tag", licence_tag));
		truck =  HibernateSupport.readOneObject(Truck.class, criterions);
		return truck;

	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#serialize()
	 */
	@Override
	public String serialize() {
		return this.licence_tag;
	}


}
