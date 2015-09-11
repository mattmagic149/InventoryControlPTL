package utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.orsoncharts.util.json.JSONArray;

import database.Inventory;
import database.Product;
import database.Truck;

public class BarCodeUtils {

	private final static int total_length = 6;
	
	public static String getBarCodeEncoding(int id) {
		String string_id = Integer.toString(id);
		int length = string_id.length();
		
		String result = string_id;
		for(int i = length; i < total_length; i++) {
			result = "0" + result;
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllTrucksAndLocationBarCodes(boolean pro, boolean tru, boolean inv) {
		
		JSONArray result = new JSONArray();
		
		if(pro) {
			List<Product> products = HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>());
			for(Product product : products) {
				result.add(product.getBarCodeEncoding());
			}
		}
	
		if(tru) {
			List<Truck> trucks = HibernateSupport.readMoreObjects(Truck.class, new ArrayList<Criterion>());
			for(Truck truck : trucks) {
				result.add(truck.getBarCodeEncoding());
			}
		}
		
		if(inv) {
			List<Inventory> inventories = HibernateSupport.readMoreObjects(Inventory.class, new ArrayList<Criterion>());
			for(Inventory inventory : inventories) {
				result.add(inventory.getBarCodeEncoding());
			}
		}
		
		return result.toJSONString();
	}
	
}
