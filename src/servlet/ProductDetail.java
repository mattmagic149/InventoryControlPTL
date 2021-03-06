package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Product;
import utils.HibernateSupport;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/ProductDetail")
public class ProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		response.setContentType("text/html; charset=UTF-8");
		if(session.getAttribute("currentUser") == null) {
			request.getRequestDispatcher("index.jsp").include(request, response);
			System.out.println("NOT logged in");
			return;
		}
		
		//ERROR:
		if(request.getParameter("id")	== null) {
			request.getRequestDispatcher("welcome.jsp").include(request, response);
			System.out.println("id = null");
			return;
		}
		
		int id;
		try {
		id = Integer.valueOf(request.getParameter("id"));
		} catch(NumberFormatException e) {
			System.out.println("Couldn't parse id");
			request.getRequestDispatcher("welcome.jsp").include(request, response);
			return;
		}
		
		if(id == 0) {
			session.setAttribute("is_new", true);
			request.setCharacterEncoding("UTF-8");
			request.getRequestDispatcher("product.jsp").include(request, response);
			return;
		} else {
			session.setAttribute("is_new", false);
		}
		
		System.out.println("id = " + id);
		Product product = HibernateSupport.readOneObjectByID(Product.class, id);

		//ERROR:
		if(product == null) {
			request.getRequestDispatcher("welcome.jsp").include(request, response);
			System.out.println("Couldn't find id in database");
			return;
		}
		
		session.setAttribute("current_product", product);
		request.getRequestDispatcher("product.jsp").include(request, response);
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
