/*
 * @Author: Matthias Ivantsits
 * Supported by TU-Graz (KTI)
 * 
 * Tool, to gather market information, in quantitative and qualitative manner.
 * Copyright (C) 2015  Matthias Ivantsits
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import database.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveLoadDatabase.
 */
public class SaveLoadDatabase {

	private String product_string = "product_backup";
	
	private String truck_string = "truck_backup";

	private String file_extension = ".csv";
	
	private String directory = "data/backups/";
	
	private PrintWriter writer;

	public SaveLoadDatabase() {
		
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SaveLoadDatabase sldb = new SaveLoadDatabase();		
		sldb.storeDatabase();
		//sldb.loadDataBase();
	}
	
	/**
	 * Store database.
	 *
	 * @return true, if successful
	 */
	public boolean storeDatabase() {
		boolean ret = true;
		
		System.out.println("Starting to store database!");

		if(!this.storeTrucks()) {
			System.out.println("ERROR: Couldn't write trucks to file!");
			ret = false;
		}
		
		if(!this.storeProducts()) {
			System.out.println("ERROR: Couldn't write products to file!");
			ret = false;
		}
		
		System.out.println("Finished storing database!");
		
		return ret;
	}

	private boolean storeProducts() {
		System.out.println("Starting to store products!");

		try {
			writer = new PrintWriter(directory + product_string + file_extension, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		
		List<Product> products = HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>());
		Product product;
		
		while(products.size() > 0) {
			product = products.get(0);
			products.remove(0);
			writer.println(product.serialize());
		}
		writer.close();

		System.out.println("Finished storing products!");
		
		return true;
	}
	
	private boolean storeTrucks() {
		System.out.println("Starting to store trucks!");

		try {
			writer = new PrintWriter(directory + truck_string + file_extension, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		
		List<Truck> trucks = HibernateSupport.readMoreObjects(Truck.class, new ArrayList<Criterion>());
		Truck truck;
		
		while(trucks.size() > 0) {
			truck = trucks.get(0);
			trucks.remove(0);
			writer.println(truck.serialize());
		}
		writer.close();

		System.out.println("Finished storing trucks!");
		
		return true;
	}
	

	public boolean loadDataBase() {
		System.out.println("Starting to load database!");
		
		if(!loadProducts()) {
			System.out.println("ERROR: Couldn't load products!");
			return false;
		}
		
		if(!loadTrucks()) {
			System.out.println("ERROR: Couldn't load trucks!");
			return false;
		}
		
		System.out.println("Finished loading database!");
		
		return true;
	}
	


	private boolean loadProducts() {
		System.out.println("Starting to load products.");
		FileReader file_reader;
		Product product;
		try {
			file_reader = new FileReader(directory + product_string + file_extension);
			BufferedReader buffered_reader = new BufferedReader(file_reader);
			String line = null;
			HibernateSupport.beginTransaction();
			while ((line = buffered_reader.readLine()) != null) {
				product = new Product(line);
				product.saveToDB();	
			}
			HibernateSupport.commitTransaction();
			buffered_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Finished loading products.");
		return true;

	}
	
	private boolean loadTrucks() {
		System.out.println("Starting to load trucks.");
		FileReader file_reader;
		Truck truck;
		try {
			file_reader = new FileReader(directory + truck_string + file_extension);
			BufferedReader buffered_reader = new BufferedReader(file_reader);
			String line = null;
			HibernateSupport.beginTransaction();
			while ((line = buffered_reader.readLine()) != null) {
				truck = new Truck(line);
				truck.saveToDB();	
			}
			HibernateSupport.commitTransaction();
			buffered_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Finished loading trucks.");
		return true;

	}

}
