package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.HibernateSupport;
import database.Location;
import database.Product;
import database.User;

/**
 * Servlet implementation class CommitTransaction
 */
@WebServlet("/CommitTransaction")
public class CommitTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommitTransaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("index.jsp").include(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("currentUser") == null) {
			request.getRequestDispatcher("index.jsp").include(request, response);
			System.out.println("NOT logged in");
			return;
		}	
		
		response.setContentType("text/html; charset=UTF-8");

		User user = (User)session.getAttribute("currentUser");
		if(user == null) {
			System.out.println("User not logged in.");
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}
		
		String from_string = request.getParameter("from");
		String to_string = request.getParameter("to");
		String product_string = request.getParameter("product_id");
		String quantity_string = request.getParameter("quantity");

		if(from_string == null || to_string == null || quantity_string == null || product_string == null) {
			System.out.println("Not all parameters are available.");
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}
		
		System.out.println("I am in CommitTransaction Servlet");
		int from_id;
		int to_id;
		int product_id;
		int quantity;
		try {
			from_id = Integer.parseInt(from_string);
			to_id = Integer.parseInt(to_string);
			quantity = Integer.parseInt(quantity_string);
			product_id = Integer.parseInt(product_string);
		} catch(NumberFormatException e) {
			System.out.println("Couldn't parse ids.");
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}

		Location from = HibernateSupport.readOneObjectByID(Location.class, from_id);
		Location to = HibernateSupport.readOneObjectByID(Location.class, to_id);
		Product product = HibernateSupport.readOneObjectByID(Product.class, product_id);
		
		if(from == null || to == null || product == null) {
			System.out.println("A location or the product is NOT present in the database.");
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}
		
		if(!user.moveNumberOfProductElements(quantity, product, to, from)) {
			System.out.println("Couldn't move prodcuts...");
			response.setStatus(401);
			response.setHeader("error_message", "Transaktion konnte nicht verbucht werden.");
			return;
		}
		
		
	}

}
