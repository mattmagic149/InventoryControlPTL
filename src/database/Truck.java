
package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.*;

import javax.persistence.*;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.javatuples.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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
	
	public enum TruckState {
		ACTIVE, SOLD
	}
	
	private String licence_tag;
	
	@ManyToOne
	@JoinColumn(name="truck_brand",updatable=false) ///TODO: nullable?!?!
	private TruckBrand truck_brand;
	
	private String type;
	
	private Date initial_registration;
	
	private Date new_vehicle_since;
	
	private int payload; //Nutzlast in kg.
	
	private FuelType type_of_fuel;
	
	private int performance; //Leistung in KW
	
	private int emission_standard; //Abgasnorm: 0-6
	
	private String fin;
	
	private TruckState state;
	
	@ManyToOne
	@JoinColumn(name="wheel_front") ///TODO: nullable?!?!
	private Wheel wheels_front;
	
	@ManyToOne
	@JoinColumn(name="wheel_rear") ///TODO: nullable?!?!
	private Wheel wheels_rear;
	
	private float loading_space_height; //Ladefläche höhe in m.
	
	private float loading_space_length;
	
	@OneToMany
	@JoinColumn(name="services")
	private List<TruckService> services;
	
	@ManyToMany(mappedBy="trucks_to_restrict")
	private List<Product> products_consumeable;
	
	public Truck() {}
	
	public Truck(String licence_tag, 
				TruckBrand truck_brand, 
				String type,
				Date initial_registration,
				Date new_vehicle_since,
				int payload,
				FuelType type_of_fuel,
				int performance,
				int emission_standard,
				String fin,
				Wheel wheels_front,
				Wheel wheels_rear,
				float loading_space_length,
				float loading_space_height,
				TruckState state
		) {
		
		this.services = new ArrayList<TruckService>();
		this.products_consumeable = new ArrayList<Product>();
		
		this.id = this.getNextId();
		this.licence_tag = licence_tag;
		this.type = type;
		
		this.truck_brand = truck_brand;
		this.truck_brand.addTruck(this);
		
		this.initial_registration = initial_registration;
		this.new_vehicle_since = new_vehicle_since;
		this.payload = payload;
		this.type_of_fuel = type_of_fuel;
		this.performance = performance;
		this.emission_standard = emission_standard;
		this.fin = fin;
		this.wheels_front = wheels_front;
		this.wheels_rear = wheels_rear;
		this.loading_space_height = loading_space_height;
		this.loading_space_length = loading_space_length;
		this.state = state;
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

	public TruckBrand getBrand() {
		return truck_brand;
	}

	public String getType() {
		return type;
	}

	public Date getInitialRegistration() {
		return initial_registration;
	}
	
	public String getInitialRegistrationFormated() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return format.format(initial_registration);
	}

	public Date getNewVehicleSince() {
		return new_vehicle_since;
	}
	
	public String getNewVehicleSinceFormated() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return format.format(new_vehicle_since);
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

	public float getLoadingSpaceLength() {
		return loading_space_length;
	}

	public List<TruckService> getServices() {
		return services;
	}

	public List<Product> getProductsConsumeable() {
		return products_consumeable;
	}

	public TruckState getTruckState() {
		return state;
	}

	public void setTruckBrand(TruckBrand truck_brand) {
		this.truck_brand = truck_brand;
	}

	public void setWheelsFront(Wheel wheels_front) {
		this.wheels_front = wheels_front;
	}

	public void setWheelsRear(Wheel wheels_rear) {
		this.wheels_rear = wheels_rear;
	}

	public void setServices(List<TruckService> services) {
		this.services = services;
	}

	public void setProductsConsumeable(List<Product> products_consumeable) {
		this.products_consumeable = products_consumeable;
	}
	
	public String getSpecificName() {
		return this.licence_tag;
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
									float loading_space_width,
									float loading_space_lenght,
									TruckState active
		) {		

		// Check, if truck already exists
		Truck truck = Truck.getTruck(licence_tag, fin);
		
		// truck exists and can't be created again
		if (truck != null) {
			return null;
		}
		
		TruckBrand truck_brand = TruckBrand.getTruckBrand(brand);
		Wheel wheels_front = new Wheel(tyre_type_front, size_in_mm_front, height_in_percent_front, size_in_inch_front);
		Wheel wheels_rear = new Wheel(tyre_type_rear, size_in_mm_rear, height_in_percent_rear, size_in_inch_rear);
		HibernateSupport.beginTransaction();
			wheels_front.saveToDB();
			wheels_rear.saveToDB();
		
			// create a new truck and set it's parameter
			Truck new_truck = new Truck(licence_tag, truck_brand, type, initial_registration, new_vehicle_since,
										payload, type_of_fuel, performance, emission_standard, fin,
										wheels_front, wheels_rear, loading_space_lenght, 
										loading_space_width, active);
		
			// Store the created truck in the DB and return it's object, in case of a successful writing.
			boolean success = new_truck.saveToDB();
		HibernateSupport.commitTransaction();
		
		if (success) {
			return new_truck;
		} else {
			return null;
		}
	}
	
	public static Truck getTruck(String licence_tag, String fin){
		
		Truck truck = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("licence_tag", licence_tag));
		criterions.add(Restrictions.eq("fin", fin));
		
		truck =  HibernateSupport.readOneObject(Truck.class, criterions);
		return truck;

	}
	
	public static int createTruckFromJSON(String object) {
		
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		Truck parsed_truck = null;
		try {
			parsed_truck = gson.fromJson(object, Truck.class);
		} catch(JsonSyntaxException e) {
			e.printStackTrace();
			return -1;
		}
		
		Truck truck = Truck.getTruck(parsed_truck.getLicenceTag(), parsed_truck.getFin());
		
		if(truck != null) {
			return truck.getId();
		}

		TruckBrand brand = TruckBrand.getTruckBrand(parsed_truck.getBrand().getName());		
		HibernateSupport.beginTransaction();
		brand.saveToDB();
		parsed_truck.setTruckBrand(brand);
		parsed_truck.getWheelsFront().saveToDB();
		parsed_truck.getWheelsRear().saveToDB();
		
		parsed_truck.id = parsed_truck.getNextId();
		parsed_truck.saveToDB();
		HibernateSupport.commitTransaction();
		
		return parsed_truck.getId();
	}
	
	public static int editTruck(String object) {
		
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		Truck parsed_truck = null;
		try {
			parsed_truck = gson.fromJson(object, Truck.class);
		} catch(JsonSyntaxException e) {
			e.printStackTrace();
			return -1;
		}
		
		
		Truck old_truck = HibernateSupport.readOneObjectByID(Truck.class, parsed_truck.getId());
		
		System.out.println("parsed_truck.id = " + parsed_truck.getId());
		if(old_truck == null) {
			System.out.println("Fetching old_truck failed...");
			return -1;
		}
		
		TruckBrand brand = TruckBrand.getTruckBrand(parsed_truck.getBrand().getName());
		HibernateSupport.beginTransaction();
		parsed_truck.wheels_front.saveToDB();
		parsed_truck.wheels_rear.saveToDB();
		brand.saveToDB();
		parsed_truck.setTruckBrand(brand);
		parsed_truck.services = old_truck.services;
		parsed_truck.product_elements = old_truck.product_elements;
		
		parsed_truck.saveToDB();
		HibernateSupport.commitTransaction();
		
		return parsed_truck.getId();
	}
	
	public static List<Truck> getAllTrucks() {
		return HibernateSupport.readMoreObjects(Truck.class, new ArrayList<Criterion>());
	}
	
	public boolean addNewTruckService(Date date, 
									  String repair_shop_name, 
									  String repair_shop_location,
									  String description,
									  int mileage) {
		
		TruckService service = new TruckService(date, 
												repair_shop_name, 
												repair_shop_location,
												description,
												mileage,
												this);
		
		HibernateSupport.beginTransaction();
		service.saveToDB();
		service.getRepairShop().addService(service);
		this.addTruckService(service);
		HibernateSupport.commitTransaction();
		
		return true;
	}

	
	public boolean addTruckService(TruckService service) {
		
		boolean success = false;
		
		if (this.services.add(service)){
			success = service.saveToDB();
		} else {
			return false;
		}
		return success;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#serialize()
	 */
	@Override
	public String serialize() {
		/*return this.id + "\t" + this.licence_tag + "\t" + this.truck_brand.getName() + "\t" + this.type + "\t" +
				this.initial_registration + "\t" + this.new_vehicle_since + "\t" +
				this.payload + "\t" + this.type_of_fuel + "\t" + this.performance + "\t" +
				this.emission_standard + "\t" + this.fin + "\t" + this.wheels_front.getTyreInfos() + "\t" +
				this.wheels_front.getSizeInmm() + "\t" + this.wheels_front.getHeightInPercent() + "\t" +
				this.wheels_front.getSizeInInch() + "\t" + this.wheels_rear.getTyreInfos() + "\t" +
				this.wheels_rear.getSizeInmm() + "\t" + this.wheels_rear.getHeightInPercent() + "\t" +
				this.wheels_rear.getSizeInInch() + "\t" + this.loading_space_height + "\t" +
				this.loading_space_width;*/
		
		List<Pair<Class<?>, List<String>>> fields_to_skip = 
				new ArrayList<Pair<Class<?>, List<String>>>();
	
		List<String> fields = new ArrayList<String>();
		fields.add("trucks_to_restrict");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(Product.class, fields));
		
		fields.clear();
		fields.add("trucks");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(TruckBrand.class, fields));
		
		fields.clear();
		fields.add("truck");
		//fields.add("repair_shop");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(TruckService.class, fields));
		
				
	
		Gson gson = new GsonBuilder()
						.addSerializationExclusionStrategy(new ProductExclusionStrategy(fields_to_skip))
						//.setPrettyPrinting()
						.create();

		return gson.toJson(this);
		
	}


}
