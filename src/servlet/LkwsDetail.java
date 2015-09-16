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

import utils.HibernateSupport;
import database.Truck;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/LkwsDetail")
public class LkwsDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LkwsDetail() {
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
		}	*/
		
		System.out.println("LkwsDetail has been called...");
		List<Truck> trucks = HibernateSupport.readMoreObjects(Truck.class, new ArrayList<Criterion>());
		System.out.println(trucks.size());
		if(trucks != null && trucks.size() > 0) {
			session.setAttribute("trucks_list", trucks);
			request.getRequestDispatcher("lkws.jsp").include(request, response);
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
