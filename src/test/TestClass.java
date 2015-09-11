package test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.orsoncharts.util.json.JSONArray;

import database.*;
import servlet.ProductDetail;
import utils.BarCodeUtils;
import utils.HibernateSupport;

public class TestClass {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>()).size());

		String object = "{\"id\":\"P-000045\",\"name\":\"Samsung Handy Neu Galaxy S4 mini\","
				+ "\"description\":\"Beschreibung...\",\"minimum_limit\":\"10\",\"lkw_ids\":[1,2,3], \"unity\":\"Stück\"}";

		System.out.println(Product.editProduct(object));
		System.out.println(HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>()).size());
		
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
