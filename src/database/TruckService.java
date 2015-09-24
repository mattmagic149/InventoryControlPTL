package database;

import interfaces.ISaveAndDelete;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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
	@JoinColumn(name="services") ///TODO: nullable?!?!
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


	public void setRepairShop(RepairShop repair_shop) {
		this.repair_shop = repair_shop;
	}

	public Truck getTruck() {
		return truck;
	}

	public static int createTruckServiceFromJSON(String object) {
		
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		TruckService parsed_truck_service = null;
		try {
			parsed_truck_service = gson.fromJson(object, TruckService.class);
		} catch(JsonSyntaxException e) {
			e.printStackTrace();
			return -1;
		}
		
		RepairShop shop;
		Truck truck = HibernateSupport.readOneObjectByID(Truck.class, parsed_truck_service.getTruck().getId());
		if(parsed_truck_service.getRepairShop().getId() != 0) {
			shop = HibernateSupport.readOneObjectByID(RepairShop.class, parsed_truck_service.getRepairShop().getId());
		} else {
			shop = RepairShop.getRepairShop(parsed_truck_service.getRepairShop().getName(),
													   parsed_truck_service.getRepairShop().getName());
		}
		if(truck == null || shop == null) {
			System.out.println("truck or shop is NULL.");
			return -1;
		}
		
		truck.addNewTruckService(parsed_truck_service.getDate(), 
								 shop.getName(), 
								 shop.getLocation(),
								 parsed_truck_service.getDescription(),
								 parsed_truck_service.getMileage());
		
		return truck.getId();
	}
	
	public static int editTruckService(String object) {
		
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		TruckService parsed_truck_service = null;
		try {
			parsed_truck_service = gson.fromJson(object, TruckService.class);
		} catch(JsonSyntaxException e) {
			e.printStackTrace();
			return -1;
		}
		
		
		RepairShop shop;
		Truck truck = HibernateSupport.readOneObjectByID(Truck.class, parsed_truck_service.getTruck().getId());
		if(parsed_truck_service.getRepairShop().getId() != 0) {
			shop = HibernateSupport.readOneObjectByID(RepairShop.class, parsed_truck_service.getRepairShop().getId());
		} else {
			shop = RepairShop.getRepairShop(parsed_truck_service.getRepairShop().getName(),
													   parsed_truck_service.getRepairShop().getName());
		}
		System.out.println(parsed_truck_service.getDate());
		System.out.println(object);

		if(truck == null || shop == null) {
			System.out.println("truck or shop is NULL.");
			return -1;
		}
		
		parsed_truck_service.setRepairShop(shop);
		HibernateSupport.beginTransaction();
		shop.saveToDB();
		parsed_truck_service.saveToDB();
		HibernateSupport.commitTransaction();
		
		return truck.getId();
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
