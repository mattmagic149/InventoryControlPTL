package database;

import java.util.ArrayList;
import java.util.List;

import interfaces.ISaveAndDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.BarCodeUtils;
import utils.HibernateSupport;
import utils.ProductExclusionStrategy;

@Entity
public class Product implements ISaveAndDelete {
	
	public enum TruckRestriction {
		YES, NO
	}
	
	public enum ProductState {
		ACTIVE, INACTIVE, NEW
	}

	@Id
	private int id;
	
	private String name;

	private String description;
		
	private int minimum_limit;
	
	@ManyToOne
	@JoinColumn(name="unity", updatable=true)
	private Unity unity;
	
	@Enumerated(value=EnumType.ORDINAL)
	private TruckRestriction restriction;
	
	@Enumerated(value=EnumType.ORDINAL)
	private ProductState state;
		
	@OneToMany
	@JoinColumn(name="product")
	private List<ProductElement> product_elements;
	
	@ManyToMany
	@JoinTable(name="TruckRestriction",
				joinColumns={@JoinColumn(name="product_id")}, 
				inverseJoinColumns={@JoinColumn(name="truck_id")})
	private List<Truck> trucks_to_restrict;
	
	public Product() {
		this.product_elements = new ArrayList<ProductElement>();
	}
	
	public Product(String serialized_product) {		
		String[] tmp = serialized_product.split("\t");
		this.name = tmp[0];
		this.description = tmp[1];
		this.unity = Unity.getUnity(tmp[2]);
		this.minimum_limit = Integer.parseInt(tmp[3]);
		this.id = this.getNextId();
	}
	
	public Product(String name, String description, int minimum_limit,
								Unity unity,
								List<Truck> trucks_to_restrict,
								TruckRestriction restriction,
								ProductState state) {
		
		this.id = this.getNextId();
		this.product_elements = new ArrayList<ProductElement>();
		this.trucks_to_restrict = new ArrayList<Truck>();
		this.name = name;
		this.description = description;
		this.minimum_limit = minimum_limit;
		this.restriction = restriction;
		this.trucks_to_restrict = trucks_to_restrict;	
		this.state = state;
		
	}
	
	public Product(int id, String name, String description, int minimum_limit,
			Unity unity,
			List<Truck> trucks_to_restrict,
			TruckRestriction restriction) {

		this.id = id;
		this.product_elements = new ArrayList<ProductElement>();
		this.trucks_to_restrict = new ArrayList<Truck>();
		this.name = name;
		this.unity = unity;
		this.description = description;
		this.minimum_limit = minimum_limit;
		this.restriction = restriction;
		this.trucks_to_restrict = trucks_to_restrict;	
	}
	
	public String getBarCodeEncoding() {
		return "P-" + BarCodeUtils.getBarCodeEncoding(id);
	}
	
	public static int createProductFromJSON(String object) {
		
		Product parsed_product = convertProductFromJSON(object);
		if(parsed_product == null) {
			return -1;
		}
		
		Product product = Product.createProduct(parsed_product.getName(),
				parsed_product.getDescription(),
				parsed_product.getMinimumLimit(),
				parsed_product.getUnity(),
				parsed_product.getTrucksToRestrict(),
				parsed_product.getRestriction(),
				parsed_product.getState());
		
		if(product == null) {
			System.out.println("couldn't create Product");
			return -1;
		}
		
		return product.getId();
	}
	
	public static boolean editProduct(String object) {
		
		Product parsed_product = convertProductFromJSON(object);
		if(parsed_product == null) {
			System.out.println("Parsing product failed...");
			return false;
		}
		
		Product old_product = HibernateSupport.readOneObjectByID(Product.class, parsed_product.getId());
		
		System.out.println("parsed_product.id = " + parsed_product.getId());
		if(old_product == null) {
			System.out.println("Fetching old_product failed...");
			return false;
		}
		
		System.out.println(parsed_product.getUnity());
		old_product.setName(parsed_product.getName());
		old_product.setDescription(parsed_product.getDescription());
		old_product.setMinimumLimit(parsed_product.getMinimumLimit());
		old_product.setUnity(parsed_product.getUnity());
		old_product.setTrucksToRestrict(parsed_product.getTrucksToRestrict());
		if(parsed_product.getTrucksToRestrict().size() > 0) {
			old_product.setRestriction(TruckRestriction.YES);
		} else {
			old_product.setRestriction(TruckRestriction.NO);
		}
		
		HibernateSupport.beginTransaction();
		old_product.saveToDB();
		HibernateSupport.commitTransaction();
		
		return true;
	}
	
	private static Product convertProductFromJSON(String object) {
		
		JSONObject obj;
		JSONArray arr;
		Truck truck;
		int truck_id;
		List<Truck> trucks = new ArrayList<Truck>();
		TruckRestriction restriction = TruckRestriction.NO;
		
		int id;
		String name;
		String description;
		int minimum_limit;
		Unity unity;
		
		try {
			obj = new JSONObject(object);
			id = BarCodeUtils.decodeBarCode(obj.get("id").toString());
			name = obj.get("name").toString();
			description = obj.get("description").toString();
			minimum_limit = Integer.parseInt(obj.get("minimum_limit").toString());
			unity = Unity.getUnity(obj.get("unity").toString());
			arr = obj.getJSONArray("lkw_ids");
			for(int i = 0; i < arr.length(); i++) {
				System.out.println(arr.get(i));
				truck_id = arr.getInt(i);
				if(truck_id > 0 && 
						(truck = HibernateSupport.readOneObjectByID(Truck.class, truck_id)) != null) {
					
					trucks.add(truck);
					restriction = TruckRestriction.YES;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("unit = " + unity);
		return new Product(id, name, description, minimum_limit, unity, trucks, restriction);
	}
	
	private int getNextId() {
		System.out.println("Product ctor");
		Criteria c = HibernateSupport.getCurrentSession().createCriteria(Product.class);
		c.addOrder(Order.desc("id"));
		c.setMaxResults(1);
		Product product = (Product)c.uniqueResult();
		
		int id = 0;
		if(product != null) {
			id = product.getId();
		}
		
		return id + 1;
	}
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getMinimumLimit() {
		return minimum_limit;
	}
	
	public ProductState getState() {
		return state;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMinimumLimit(int minimum_limit) {
		this.minimum_limit = minimum_limit;
	}

	public void setUnity(Unity unity) {
		this.unity = unity;
	}

	public void setRestriction(TruckRestriction restriction) {
		this.restriction = restriction;
	}

	public void setProductElements(List<ProductElement> product_elements) {
		this.product_elements = product_elements;
	}

	public void setTrucksToRestrict(List<Truck> trucks_to_restrict) {
		this.trucks_to_restrict = trucks_to_restrict;
	}

	public boolean addProductElement(ProductElement elem) {
		boolean success = false;
		
		if(!this.product_elements.contains(elem)) {
			if (this.product_elements.add(elem)){
				success = elem.saveToDB();
			} else {
				return false;
			}
		}
		
		return success;
	}
	
	public static Product createProduct(String name, String description, 
										int minimum_limit,
										Unity unity,
										List<Truck> trucks_to_restrict, 
										TruckRestriction restriction,
										ProductState state
		) {		
		// Check, if product already exists
		Product product = Product.getProduct(name, description);
		
		// product exists and can't be created again
		if (product != null) {
			return null;
		}
		
		HibernateSupport.beginTransaction();
			// create a new product and set it's parameter
			Product new_product = new Product(name, description, 
												minimum_limit, 
												unity, 
												trucks_to_restrict, 
												restriction,
												state
											);
			
			// Store the created product in the DB and return it's object, in case of a successful writing.
			boolean success = new_product.saveToDB();
		HibernateSupport.commitTransaction();
		
		if (success) {
			return new_product;
		} else {
			return null;
		}
	}
	
	public static Product getProduct(String name){
		
		Product product = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		product =  HibernateSupport.readOneObject(Product.class, criterions);
		return product;

	}
	
	public static Product getProduct(String name, String description){
		
		Product product = null;
		List<Criterion>  criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("name", name));
		criterions.add(Restrictions.eq("description", description));
		product =  HibernateSupport.readOneObject(Product.class, criterions);
		return product;

	}
	
	public static Product getProduct(int id){
		return HibernateSupport.readOneObjectByID(Product.class, id);
	}

	public Unity getUnity() {
		return unity;
	}

	public TruckRestriction getRestriction() {
		return restriction;
	}

	public List<ProductElement> getProductElements() {
		return product_elements;
	}

	public List<Truck> getTrucksToRestrict() {
		return trucks_to_restrict;
	}

	@Override
	public String serialize() {
		/*return this.name + "\t" + this.description + "\t" +  this.unity + "\t" + 
				this.minimum_limit + "\t" + this.id;*/
		
		List<Pair<Class<?>, List<String>>> fields_to_skip = 
				new ArrayList<Pair<Class<?>, List<String>>>();
	
		List<String> fields = new ArrayList<String>();
		fields.add("product_elements");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(Product.class, fields));
				
		fields.clear();
		fields.add("products_consumeable");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(Truck.class, fields));
		
		fields.clear();
		fields.add("products");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(Unity.class, fields));
	
		Gson gson = new GsonBuilder()
						.addSerializationExclusionStrategy(new ProductExclusionStrategy(fields_to_skip))
						//.setPrettyPrinting()
						.create();

		return gson.toJson(this);
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
