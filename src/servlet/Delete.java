package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.HibernateSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import database.Product;
import database.Truck;
import database.TruckService;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
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
		String object = new String(request.getParameter("object").getBytes("iso-8859-1"),"UTF-8");
		if(parameter == null || object == null) {
			response.setStatus(401);
			response.setHeader("error_message", "Ungültige Anfrage.");
			return;
		}
		System.out.println("I am in Delete Servlet");
		if(parameter.equals("truck_service")) {
			System.out.println("I am deleting a TruckService");
			Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
			TruckService service;
			System.out.println(object);
			
			try {
				service = gson.fromJson(object, TruckService.class);
			} catch(JsonSyntaxException e) {
				System.out.println("Couldn't parse TruckService");
				response.setStatus(401);
				response.setHeader("error_message", "Ungültige Anfrage.");
				e.printStackTrace();
				return;
			}
				
			HibernateSupport.beginTransaction();
			service.deleteFromDB(service);
			HibernateSupport.commitTransaction();

		}
		
	}

}
