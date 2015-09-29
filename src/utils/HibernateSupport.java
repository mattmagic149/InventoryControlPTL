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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import database.*;



// TODO: Auto-generated Javadoc
/**
 * This Class handles everything for communicating with the database.
 */

@SuppressWarnings("deprecation")
public class HibernateSupport {
		
	/** The session factory. */
	private static SessionFactory sessionFactory;

	static {
		System.out.println("HibernateSupport: Constructor");
		init();
	}
	
	/**
	 * Creates the.
	 */
	public static void create(){
		// function is not necessary it only activates the static construction above
	}
	
	/**
	 * Inits the.
	 */
	private static void init() {
		//Change the path to your deployed config file !
		
		//File configFile = new File("/Users/matthiasivantsits/Documents/workspace/InventoryControlPTL/src/hibernate.cfg.xml");
		//File configFile = new File("C:/xampp/htdocs/InventoryControlPTL/src/hibernate.cfg.xml");
		File configFile = new File("C:/xampp/tomcat/webapps/InventoryControlPTL/hibernate.cfg.xml");
		//File configFile = new File("hibernate.cfg.xml");

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

		configuration.configure(configFile);
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	/**
	 * Gets the current session.
	 *
	 * @return the current session
	 */
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	/**
	 * Begin transaction.
	 */
	public static void beginTransaction() {
		
		if (getCurrentSession().getTransaction() != null
	            && getCurrentSession().getTransaction().isActive()) {
			
	        getCurrentSession().getTransaction();
	    } else {
	        getCurrentSession().beginTransaction();
	    }
		
        //getCurrentSession().beginTransaction();

		
		//getCurrentSession().beginTransaction();
	}
	
	/**
	 * Commit transaction.
	 */
	public static void commitTransaction() {
		getCurrentSession().getTransaction().commit();
	}
	
	/**
	 * Commit.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	public static boolean commit(Object obj) {
		try {
			getCurrentSession().saveOrUpdate(obj);
		} catch (HibernateException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Read more objects.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param criterions the criterions
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static <T> List <T> readMoreObjects(Class<?> classToRetrieve, List<Criterion> criterions) {
		beginTransaction();
		Criteria criteria = getCurrentSession().createCriteria(classToRetrieve);
		for(Criterion criterion: criterions) {
			criteria.add(criterion);
		}
		List<T> result = criteria.list();
		commitTransaction();
		return result;
	}
	
	/**
	 * Read more objects desc.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param criterions the criterions
	 * @param field the field
	 * @return the array list
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList <T> readMoreObjectsDesc(Class<?> classToRetrieve, List<Criterion> criterions, String field) {
		beginTransaction();
		Criteria criteria = getCurrentSession().createCriteria(classToRetrieve).addOrder(Order.asc(field));
		for(Criterion criterion: criterions) {
			criteria.add(criterion);
		}
		List<T> result = criteria.list();
		commitTransaction();
		return (ArrayList<T>) result;
	}
	
	/**
	 * Read more objects.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param criterions the criterions
	 * @param offset the offset
	 * @param increment_by the increment_by
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static <T> List <T> readMoreObjects(Class<?> classToRetrieve, List<Criterion> criterions, int offset, int increment_by) {
		beginTransaction();
		Criteria criteria = getCurrentSession().createCriteria(classToRetrieve);
		for(Criterion criterion: criterions) {
			criteria.add(criterion);
		}
		criteria.setFirstResult(offset);
		criteria.setMaxResults(increment_by);
		List<T> result = criteria.list();
		commitTransaction();
		return result;
	}
	
	/**
	 * Read one object.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param criterions the criterions
	 * @return the t
	 */
	public static <T> T readOneObject(Class<?> classToRetrieve, List<Criterion> criterions) {
		List<T> result = readMoreObjects(classToRetrieve, criterions);
		return (result.size() > 0) ? (result.get(0)):(null);
	}
	
	/**
	 * Read one object by id.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param id the id
	 * @return the t
	 */
	public static <T> T readOneObjectByID(Class<?> classToRetrieve, int id) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.idEq(id));
		T result = readOneObject(classToRetrieve, criterions);
		return result;
	}
	
	/**
	 * Read one object by id.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param id the id
	 * @return the t
	 */
	public static <T> T readOneObjectByLongID(Class<?> classToRetrieve, long id) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.idEq(id));
		T result = readOneObject(classToRetrieve, criterions);
		return result;
	}
	
	/**
	 * Read one object by id.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param id the id
	 * @return the t
	 */
	public static <T> T readOneObjectByID(Class<?> classToRetrieve, long id) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.idEq(id));
		T result = readOneObject(classToRetrieve, criterions);
		return result;
	}
	
	/**
	 * Read one object by string id.
	 *
	 * @param <T> the generic type
	 * @param classToRetrieve the class to retrieve
	 * @param id the id
	 * @return the t
	 */
	public static <T> T readOneObjectByStringId(Class<?> classToRetrieve, String id) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.idEq(id));
		T result = readOneObject(classToRetrieve, criterions);
		return result;
	}
	
	
	/**
	 * Delete object.
	 *
	 * @param <T> the generic type
	 * @param objectToDelete the object to delete
	 */
	public static <T> void deleteObject(T objectToDelete) {
		getCurrentSession().delete(objectToDelete);
	}
	


}
