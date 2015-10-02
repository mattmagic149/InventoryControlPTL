package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Product;
import database.Truck;
import database.TruckService;

/**
 * Servlet implementation class Add
 */
@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
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
		String parameter = request.getParameter("type");
//		String object = new String(request.getParameter("object").getBytes("iso-8859-1"),"UTF-8");
		String object = request.getParameter("object");
		if(parameter == null || object == null) {
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}
		System.out.println("I am in Add Servlet");
		int id;
		if(parameter.equals("product")) {
			System.out.println("I am adding a new Product");
			if((id = Product.createProductFromJSON(object)) == -1) {
				response.setStatus(401);
				response.setHeader("error_message", "Produkt konnte nicht hinzugefügt werden.");
				return;
			} else {
				response.setHeader("id", String.valueOf(id));
				return;
			}
		} else if(parameter.equals("truck")) {
			System.out.println("I am adding a new Truck");
			if((id = Truck.createTruckFromJSON(object)) == -1) {
				response.setStatus(401);
				response.setHeader("error_message", "Lkw konnte nicht hinzugefügt werden.");
				return;
			} else {
				response.setHeader("id", String.valueOf(id));
				return;
			}
		} else if(parameter.equals("truck_service")) {
			System.out.println("I am adding a new TruckService");
			if((id = TruckService.createTruckServiceFromJSON(object)) == -1) {
				response.setStatus(401);
				response.setHeader("error_message", "Service konnte nicht hinzugefügt werden.");
				return;
			} else {
				response.setHeader("id", String.valueOf(id));
				return;
			}
		}
		
	}

}
