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

import database.Product;
import database.Truck;
import utils.HibernateSupport;

@WebServlet("/PrintLabels")
public class PrintLabels extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintLabels() {
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
		System.out.println("PrintLabels has been called...");

		List<Product> products;
		products = HibernateSupport.readMoreObjects(Product.class, new ArrayList<Criterion>());

		List<Truck> trucks;
		trucks = HibernateSupport.readMoreObjects(Truck.class, new ArrayList<Criterion>());
		
		if(products != null && trucks != null) {
			session.setAttribute("products_list", products);
			session.setAttribute("trucks_list", trucks);
			request.getRequestDispatcher("print_labels.jsp").include(request, response);
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
