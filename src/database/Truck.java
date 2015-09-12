
package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.*;

import javax.persistence.*;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import database.Wheel.TyreType;
import utils.*;

/**
 * The Class Truck.
 */
@Entity
public class Truck extends Location implements ISaveAndDelete {
	
	public enum FuelType {
		DIESEL, PETROL
	}
	
	private String licence_tag;
	
	private String brand;
	
	private String type;
	
	private Date initial_registration;
	
	private Date new_vehicle_since;
	
	private int payload; //Nutzlast in kg.
	
	private FuelType type_of_fuel;
	
	private int performance; //Leistung in KW
	
	private int emission_standard; //Abgasnorm: 0-6
	
	private String fin;
	
	@ManyToOne
	@JoinColumn(name="wheel_front",updatable=false) ///TODO: nullable?!?!
	private Wheel wheels_front;
	
	@ManyToOne
	@JoinColumn(name="wheel_rear",updatable=false) ///TODO: nullable?!?!
	private Wheel wheels_rear;
	
	private float loading_space_height; //Ladefläche höhe in m.
	
	private float loading_space_width;
	
	@OneToMany
	@JoinColumn(name="service")
	private List<TruckService> services;
	
	@ManyToMany(mappedBy="trucks_to_restrict")
	private List<Product> products_consumeable;
	
	public Truck() {}
	
	public Truck(String licence_tag, 
				String brand, 
				String type,
				Date initial_registration,
				Date new_vehicle_since,
				int payload,
				FuelType type_of_fuel,
				int performance,
				int emission_standard,
				String fin,
				TyreType tyre_type_front,
				int size_in_mm_front,
				int height_in_percent_front,
				float size_in_inch_front,
				TyreType tyre_type_rear,
				int size_in_mm_rear,
				int height_in_percent_rear,
				float size_in_inch_rear,
				float loading_space_height,
				float loading_space_width) {
		
		this.id = this.getNextId();
		this.licence_tag = licence_tag;
		this.brand = brand;
		this.initial_registration = initial_registration;
		this.new_vehicle_since = new_vehicle_since;
		this.payload = payload;
		this.type_of_fuel = type_of_fuel;
		this.performance = performance;
		this.emission_standard = emission_standard;
		this.fin = fin;
		this.wheels_front = new Wheel(tyre_type_front, size_in_mm_front, height_in_percent_front, size_in_inch_front);
		this.wheels_rear = new Wheel(tyre_type_rear, size_in_mm_rear, height_in_percent_rear, size_in_inch_rear);
		this.loading_space_height = loading_space_height;
		this.loading_space_width = loading_space_width;
	}
	
	public Truck(String serialized_truck) {
		String[] tmp = serialized_truck.split("\t");
		this.licence_tag = tmp[0];
		this.id = Integer.parseInt(tmp[1]);
		this.brand = tmp[2];
	}
	
	public String getBarCodeEncoding() {
		return "L-" + BarCodeUtils.getBarCodeEncoding(id);
	}
		
	public String getLicenceTag() {
		return licence_tag;
	}
	
	public Wheel getWheelsRear() {
		return wheels_rear;
	}

	public String getLicence_tag() {
		return licence_tag;
	}

	public String getBrand() {
		return brand;
	}

	public String getType() {
		return type;
	}

	public Date getInitialRegistration() {
		return initial_registration;
	}

	public Date getNewVehicleSince() {
		return new_vehicle_since;
	}

	public int getPayload() {
		return payload;
	}

	public FuelType getTypeOfFuel() {
		return type_of_fuel;
	}

	public int getPerformance() {
		return performance;
	}

	public int getEmissionStandard() {
		return emission_standard;
	}

	public String getFin() {
		return fin;
	}

	public Wheel getWheelsFront() {
		return wheels_front;
	}

	public float getLoadingSpaceHeight() {
		return loading_space_height;
	}

	public float getLoadingSpaceWidth() {
		return loading_space_width;
	}

	public List<TruckService> getServices() {
		return services;
	}

	public List<Product> getProductsConsumeable() {
		return products_consumeable;
	}

	public static Truck createTruck(String licence_tag, 
									String brand, 
									String type,
									Date initial_registration,
									Date new_vehicle_since,
									int payload,
									FuelType type_of_fuel,
									int performance,
									int emission_standard,
									String fin,
									TyreType tyre_type_front,
									int size_in_mm_front,
									int height_in_percent_front,
									float size_in_inch_front,
									TyreType tyre_type_rear,
									int size_in_mm_rear,
									int height_in_percent_rear,
									float size_in_inch_rear,
									float loading_space_height,
									float loading_space_width) {		

		// Check, if truck already exists
		Truck truck = Truck.getTruck(licence_tag);
		
		// truck exists and can't be created again
		if (truck != null) {
			return null;
		}
		
		// create a new truck and set it's parameter
		Truck new_truck = new Truck(licence_tag, brand, type, initial_registration, new_vehicle_since,
									payload, type_of_fuel, performance, emission_standard,  fin,
									tyre_type_front, size_in_mm_front, height_in_percent_front, size_in_inch_front,
									tyre_type_rear, size_in_mm_rear, height_in_percent_rear, size_in_inch_rear,
									loading_space_height, loading_space_width);
		
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
	
	public boolean addTruckService() {
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#serialize()
	 */
	@Override
	public String serialize() {
		return this.licence_tag + "\t" + this.id + "\t" + this.brand;
	}


}
