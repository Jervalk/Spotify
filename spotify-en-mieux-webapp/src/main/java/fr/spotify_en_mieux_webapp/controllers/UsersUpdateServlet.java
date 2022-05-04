package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.models.UserListener;
import fr.spotify_en_mieux_core.models.UserProducer;
import fr.spotify_en_mieux_core.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet({"/users/update"})
public class UsersUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserRepository ur = new UserRepository();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve request parameter (id)
		int id = Integer.parseInt(request.getParameter("id"));
		// retrieve user from DB
		User u = ur.findById(id).orElseThrow();
		// pass user object to jsp (request attribute)
		request.setAttribute("editedUser", u);
		// delegate response creation to jsp
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/usersUpdate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve request parameter
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		int id = Integer.parseInt(request.getParameter("id"));
		// build a User object
		User u = null;
		switch (role) {
		case "ROLE_ADMIN":
			u = new User();
			break;
		case "ROLE_USER":
			u = new UserListener();
			break;
		case "ROLE_PRODUCER":
			u = new UserProducer();
			break;
		}
		u.setUsername(username);
		u.setPassword(password);
		u.setEmail(email);
		u.setRole(role);
		u.setId(id);
		// validating user
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> errors =  validator.validate(u);
		if (errors.isEmpty()) {
			// save object to DB
			ur.update(u);
			// redirect to users list
			response.sendRedirect(request.getContextPath() + "/users");
		} else {
			
			request.setAttribute("errors", errors.stream()
				.collect(Collectors.groupingBy(
						cv -> cv.getPropertyPath().toString(),
						Collectors.mapping(cv -> cv.getMessage(), Collectors.toList()))));
			
			request.setAttribute("editedUser", u);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/usersUpdate.jsp").forward(request, response);
		}
	}

}
