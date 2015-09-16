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

import database.Inventory;
import database.Product;
import utils.HibernateSupport;

@WebServlet("/ProductsOverview")
public class ProductsOverview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsOverview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession(true);
		
		/*if(session.getAttribute("currentUser") == null) {
			request.getRequestDispatcher("index.jsp").include(request, response);
			System.out.println("NOT logged in");
			return;
		}*/
		System.out.println("ProductsOverview has been called...");

		String minimum_string = request.getParameter("show_under_minimum_only");
		List<Product> products;
		if(minimum_string != null && minimum_string.equals("true")) {
			Inventory inventory = HibernateSupport.readOneObjectByID(Inventory.class, 2);
			products = inventory.getAllProductsUnderMinimumLimit();
		} else {
			products = HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>());
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
		request.getRequestDispatcher("welcome.jsp").include(request, response);
		return;
	}

}
