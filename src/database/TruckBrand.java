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
public class TruckBrand implements ISaveAndDelete {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(name="truck")
	private List<Truck> trucks;
	
	public TruckBrand() {
		this.trucks = new ArrayList<Truck>();
	}
	
	public TruckBrand(String name) {
		this.trucks = new ArrayList<Truck>();
		this.name = name;
	}
	
	public static TruckBrand getTruckBrand(String name){
		
		TruckBrand truck_brand = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		truck_brand =  HibernateSupport.readOneObject(TruckBrand.class, criterions);
		if(truck_brand == null) {
			truck_brand = new TruckBrand(name);
			HibernateSupport.beginTransaction();
			truck_brand.saveToDB();
			HibernateSupport.commitTransaction();
		}
		
		return truck_brand;

	}
	
	public boolean addTruck(Truck truck) {
		boolean success = false;
		
		if(!this.containsTruck(truck)) {
			if (this.trucks.add(truck)){
				success = truck.saveToDB();
			} else {
				return false;
			}
		}
		
		return success;
	}
	
	public boolean containsTruck(Truck truck) {
		for(int i = 0; i < this.trucks.size(); i++) {
			if(truck.getId() == this.trucks.get(i).getId()) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String serialize() {
		return null;
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
}
