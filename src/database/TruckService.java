package database;

import interfaces.ISaveAndDelete;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import utils.HibernateSupport;

@Entity
public class TruckService implements ISaveAndDelete {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="repair_shop", updatable=false) ///TODO: nullable?!?!
	private RepairShop repair_shop;
	
	private String description;
	
	private int mileage; //Kilometerstand in km
	
	@ManyToOne
	@JoinColumn(name="truck") ///TODO: nullable?!?!
	private Truck truck;
	
	public TruckService() {}
	
	public TruckService(Date date, String repair_shop_name, 
								   String repair_shop_name_location,
								   String description,
								   int mileage,
								   Truck truck) {
		this.date = date;
		this.description = description;
		this.repair_shop = RepairShop.getRepairShop(repair_shop_name, repair_shop_name_location);
		this.mileage = mileage;
		this.repair_shop.addService(this);
		this.truck = truck;
		
	}
	
	
	public int getId() {
		return id;
	}


	public Date getDate() {
		return date;
	}


	public RepairShop getRepairShop() {
		return repair_shop;
	}


	public String getDescription() {
		return description;
	}


	public int getMileage() {
		return mileage;
	}


	public Truck getTruck() {
		return truck;
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
