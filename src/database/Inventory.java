
package database;

import javax.persistence.*;

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
		return "fuck yeah";
	}


}
