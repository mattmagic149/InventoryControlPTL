package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.HibernateSupport;
import database.Truck;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/LkwDetail")
public class LkwDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LkwDetail() {
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
			request.getRequestDispatcher("lkw.jsp").include(request, response);
			request.setCharacterEncoding("UTF-8");
			return;
		} else {
			session.setAttribute("is_new", false);
		}
		
		System.out.println("id = " + id);
		Truck truck = HibernateSupport.readOneObjectByID(Truck.class, id);

		//ERROR:
		if(truck == null) {
			request.getRequestDispatcher("welcome.jsp").include(request, response);
			System.out.println("Couldn't find id in database");
			return;
		}
		
		session.setAttribute("current_truck", truck);
		request.getRequestDispatcher("lkw.jsp").include(request, response);
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
