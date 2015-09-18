package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Product;
import database.Truck;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("welcome.jsp").include(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parameter = request.getParameter("type");
		String object = request.getParameter("object");
		if(parameter == null || object == null) {
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}
		System.out.println("I am in Edit Servlet");
		
		int id = -1;
		if(parameter.equals("product")) {
			System.out.println("I am editing a Product");
			if((id = Product.editProduct(object)) == -1) {
				response.setStatus(401);
				response.setHeader("error_message", "Produkt konnte nicht bearbeitet werden.");
				return;
			} else {
				response.setHeader("id", String.valueOf(id));
				return;
			}
		} else if(parameter.equals("truck")) {
			System.out.println("I am editing a Truck");
			if((id = Truck.editTruck(object)) == -1) {
				response.setStatus(401);
				response.setHeader("error_message", "Produkt konnte nicht bearbeitet werden.");
				return;
			} else {
				response.setHeader("id", String.valueOf(id));
				return;
			}
		}
	}

}
