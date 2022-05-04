package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.repositories.UserRepository;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet({"/signin", "/login"})
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserRepository ur = new UserRepository();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve request parameter
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// searching credentials
		Optional<User> user = ur.findByUsernameAndPassword(username, password);
		
		if (user.isPresent()) {
			// save user in session attribute
			request.getSession().setAttribute("user", user.get());
			// redirect to users list
			response.sendRedirect(request.getContextPath() + "/musics");
		} else {
			request.setAttribute("errors", List.of("Invalid username or password"));
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/signin.jsp").forward(request, response);
		}
	}

}
