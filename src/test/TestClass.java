package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.HibernateSupport;
import utils.ProductExclusionStrategy;
import database.*;
import database.Truck.FuelType;
import database.Wheel.TyreType;

public class TestClass {

	public static void main(String[] args) throws InterruptedException, ParseException {
		
		System.out.println(HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>()).size());

		String object = "{\"id\":\"P-000044\",\"name\":\"Samsung Handy Neu Galaxy S4 mini\","
				+ "\"description\":\"Beschreibung...\",\"minimum_limit\":\"10\",\"lkw_ids\":[1,2,3], \"unity\":\"Stück\"}";

		System.out.println(Product.editProduct(object));
		System.out.println(HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>()).size());
		
		//System.out.println(new Wheel(TyreType.RADIAL, 195, 70, 15).getTyreInfos());
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
		
		Truck truck = Truck.createTruck("GU PTL 11", 
				"Mercedes Benz", 
				"Actros",
				format.parse("26.09.13"),
				format.parse("21.11.14"),
				23500,
				Truck.FuelType.DIESEL,
				350,
				6,
				"WDB9634031L803044",
				Wheel.TyreType.RADIAL,
				385,
				55,
				22.5f,
				Wheel.TyreType.RADIAL,
				315,
				70,
				22.5f,
				13.6f,
				2.73f);
		
		truck = Truck.getTruck("GU PTL 11");
		Product product = Product.getProduct(44);
		
		
		
		List<Pair<Class<?>, List<String>>> fields_to_skip = 
					new ArrayList<Pair<Class<?>, List<String>>>();
		
		List<String> fields = new ArrayList<String>();
		fields.add("product_elements");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(Product.class, fields));
				
		fields.clear();
		fields.add("products_consumeable");
		fields_to_skip.add(new Pair<Class<?>, List<String>>(Truck.class, fields));

		Gson gson = new GsonBuilder()
						.addSerializationExclusionStrategy(new ProductExclusionStrategy(fields_to_skip))
						.setPrettyPrinting()
						.create();
		//System.out.println(gson.toJson(truck));
		System.out.println(gson.toJson(product));
		String json = gson.toJson(product);
		
		Product deserialized_product = gson.fromJson(json, Product.class);
		System.out.println(deserialized_product.getTrucksToRestrict().size());
		
		
		/*String username = "rene";
		String password = "123456";
		
		Truck truck1 = Truck.getTruck("GU PTL 12");
		Truck truck2 = Truck.getTruck("GU PTL 13");
		
		if(truck1 == null || truck2 == null) {
			System.out.println("Could not load trucks from db...");
			return;
		}
		
		ProductElement elem1 = new ProductElement();
		ProductElement elem2 = new ProductElement();
		ProductElement elem3 = new ProductElement();
		ProductElement elem4 = new ProductElement();
		ProductElement elem5 = new ProductElement();
		
		HibernateSupport.beginTransaction();
		
			Product product = new Product("name", "description", 10, 
				new ArrayList<Truck>(), 
				Product.TruckRestriction.NO);
		
			product.saveToDB();
			product.addProductElement(elem1);
			product.addProductElement(elem2);
			product.addProductElement(elem3);
			product.addProductElement(elem4);
			product.addProductElement(elem5);
			
			System.out.println(truck1.addProductElement(elem1));
			truck1.addProductElement(elem2);
			truck1.addProductElement(elem3);
			truck1.addProductElement(elem4);
			truck1.addProductElement(elem5);
			
		HibernateSupport.commitTransaction();
		
		User user = User.login(username, password);
		System.out.println(user.moveNumberOfProductElements(6, product, truck2, truck1));*/
		
	}

}
