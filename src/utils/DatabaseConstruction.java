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

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import database.*;


// TODO: Auto-generated Javadoc
/**
 * This Class is for Constructing the Database.
 *
 */

public class DatabaseConstruction {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Creating database-structure for AnSoMia");
		
		Configuration configuration = new Configuration();
		
		//add all classes you want to annotate
		configuration.addAnnotatedClass(Location.class);
		configuration.addAnnotatedClass(Truck.class);
		configuration.addAnnotatedClass(Inventory.class);
		configuration.addAnnotatedClass(Product.class);
		configuration.addAnnotatedClass(Unity.class);
		configuration.addAnnotatedClass(ProductElement.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Transaction.class);

		configuration.addAnnotatedClass(TruckService.class);
		configuration.addAnnotatedClass(Wheel.class);
		configuration.addAnnotatedClass(TruckBrand.class);
		configuration.addAnnotatedClass(RepairShop.class);

		
		configuration.configure("hibernate.cfg.xml");

		new SchemaExport(configuration).create(true, true);
		
		SaveLoadDatabase sldb = new SaveLoadDatabase();
		sldb.loadDataBase();
		

		String username = "rene";
		String password = "123456";
		User user = User.register(username, "Rene", "Pellissier", "matthiasivantsits@gmail.com", password, User.Permission.ADMIN);
		
		String username2 = "matt";
		String password2 = "123456";
		User user2 = User.register(username2, "Matt", "Nachname", "bla@gmail.com", password2, User.Permission.USER);

		HibernateSupport.beginTransaction();
			user.saveToDB();
			user2.saveToDB();
		HibernateSupport.commitTransaction();

		
		System.out.println("Finished");
	}

}
