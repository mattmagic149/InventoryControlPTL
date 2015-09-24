package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import database.Inventory;
import database.Product;
import database.RepairShop;
import database.Truck;
import database.TruckService;
import utils.HibernateSupport;

public class TestClass {

	public static void main(String[] args) throws ParseException {
		Truck truck = HibernateSupport.readOneObjectByID(Truck.class, 3);
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		truck.addNewTruckService(format.parse("10.09.2015"), "Weichberger", 
				   "Österreich/Unterpremstätten",
				   "4 Stück Brightstone Antrieb neu",
				   291510);
		
		
		
		
	}

}
