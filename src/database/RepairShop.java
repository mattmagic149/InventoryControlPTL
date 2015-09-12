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
public class RepairShop implements ISaveAndDelete {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	private String name;
	
	private String location;
	
	@OneToMany
	@JoinColumn(name="service")
	private List<TruckService> services;
	
	public RepairShop(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	public static RepairShop getRepairShop(String name, String location) {
		
		RepairShop repair_shop = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		criterions.add(Restrictions.eq("location", location));
		repair_shop =  HibernateSupport.readOneObject(RepairShop.class, criterions);

		if(repair_shop == null) {
			repair_shop = new RepairShop(name, location);
		}
		
		HibernateSupport.beginTransaction();
		repair_shop.saveToDB();
		HibernateSupport.commitTransaction();
		
		return repair_shop;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public List<TruckService> getServices() {
		return services;
	}
	
	public boolean addService(TruckService service) {
		boolean success = false;
		
		if(!this.services.contains(service)) {
			if (this.services.add(service)){
				success = service.saveToDB();
			} else {
				return false;
			}
		}
		
		return success;
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
