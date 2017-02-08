package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Criterion;
import org.javatuples.Pair;

import database.Inventory;
import database.Location;
import database.Product;
import database.Transaction;
import utils.HibernateSupport;

@WebServlet("/TransactionsOverview")
public class TransactionsOverview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionsOverview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("currentUser") == null) {
			request.getRequestDispatcher("index.jsp").include(request, response);
			System.out.println("NOT logged in");
			return;
		}
		System.out.println("ProductsOverview has been called...");
	
		String location_id_string = request.getParameter("location_id");
		List<Product> products;
		if(location_id_string != null) {
			int location_id;
			try {
				location_id = Integer.parseInt(location_id_string);
			} catch(NumberFormatException e) {
				System.out.println("Couldn't parse ids.");
				response.setStatus(401);
				response.setHeader("error_message", "Ungültige Anfrage.");
				return;
			}
			
			
			Location location = HibernateSupport.readOneObjectByID(Location.class, location_id);
			List<Pair<Long, Transaction>> transations = location.getTransactionsWithQuantity();
			
			System.out.println("WAAAAS!?!?");
			for(Pair<Long, Transaction> transaction : transations) {
				System.out.println(transaction.getValue0());
				System.out.println(transaction.getValue1().getDateMoved());
				System.out.println(transaction.getValue1().getElements().get(0).getProduct().getDescription());
				System.out.println("----------------------------------------");
			}
			
			session.setAttribute("transactions_list", transations);
			session.setAttribute("details", false);
			session.setAttribute("location", location);
			request.getRequestDispatcher("transactions.jsp").include(request, response);
			return;
		}
		
		Location location = HibernateSupport.readOneObjectByID(Location.class, 2);
		
		session.setAttribute("details", true);
		session.setAttribute("location", location); //this is the main inventory
		String minimum_string = request.getParameter("show_under_minimum_only");
		if(minimum_string != null && minimum_string.equals("true")) {
			Inventory inventory = HibernateSupport.readOneObjectByID(Inventory.class, 2);
			products = inventory.getAllProductsUnderMinimumLimit();
		} else {
			products = HibernateSupport.readMoreObjectsDesc(Product.class, new ArrayList<Criterion>(), "name");
		}
				
		if(products != null) {
			session.setAttribute("products_list", products);
			request.getRequestDispatcher("products.jsp").include(request, response);
			return;
		}
		
		request.getRequestDispatcher("welcome.jsp").include(request, response);
		return;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("index.jsp").include(request, response);
		return;
	}

}
