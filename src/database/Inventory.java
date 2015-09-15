
package database;


import javax.persistence.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.BarCodeUtils;

/**
 * The Class Truck.
 */
@Entity
public class Inventory extends Location {
	
	private String name;
	
	private String location;
	
	public Inventory() {
		super();
	}
	
	public Inventory(String name, String location) {
		this.id = this.getNextId();
		this.name = name;
		this.location = location;
	}
	
	public String getBarCodeEncoding() {
		return "I-" + BarCodeUtils.getBarCodeEncoding(id);
	}
	
	public String getName() {
		return name;
	}


	public String getLocation() {
		return location;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#serialize()
	 */
	@Override
	public String serialize() {
		
		Gson gson = new GsonBuilder().create();

		return gson.toJson(this);
	}


}
