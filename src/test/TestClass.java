package test;

import java.util.List;

import database.Inventory;
import database.Product;
import utils.HibernateSupport;

public class TestClass {

	public static void main(String[] args) {
		Inventory inventory = HibernateSupport.readOneObjectByID(Inventory.class, 2);
		List<Product> products = inventory.getAllProductsUnderMinimumLimit();
		
		System.out.println(products.size());
		
	}

}
