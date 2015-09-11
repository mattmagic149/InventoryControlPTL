package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").include(request, response);
		System.out.println("I AM HERE!!!");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// try to login
		User user;
		if((user = User.login(email, password)) != null){
			// login successful
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", user);
			
			request.getRequestDispatcher("welcome.jsp").include(request, response);
			return;
		}
				
		// login failed
		response.setStatus(401);
		response.setHeader("error_message", "Login fehlgeschlagen!");
		//response.setCharacterEncoding("UTF-8");
		//response.getWriter().write("Login fehlgeschlagen! Bitte E-Mail und Passwort überprüfen.");
		return;
		
	}

}
