package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet({"/signup", "/users/add"})
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserRepository ur = new UserRepository();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/signup.jsp").forward(request, response);
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
		// build a User object
		User u;
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
		default:
			throw new RuntimeException("invalid user role " + role);
		}
		u.setUsername(username);
		u.setPassword(password);
		u.setEmail(email);
		u.setRole(role);
		// validating user
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> errors =  validator.validate(u);
		if (errors.isEmpty()) {
			// save object to DB
			System.out.println(u);
			ur.save(u);
			System.out.println("done");
			// redirect to users list
			response.sendRedirect(request.getContextPath() + "/users");
		} else {
			
//			Map<String, List<String>> map = new HashMap<>();
//			for (var cv : errors) {
//				String k = cv.getPropertyPath().toString();
//				List<String> v = List.of(cv.getMessage());
//				if (!map.containsKey(k)) {
//					map.put(k, v);
//				} else {
//					var l = new ArrayList<String>();
//					l.addAll(map.get(k));
//					l.addAll(v);
//					map.put(k, l);
//				}
//			}
//			
//			errors.stream()
//				.collect(Collectors.toMap(
//						cv -> cv.getPropertyPath().toString(),
//						cv -> List.of(cv.getMessage()), 
//						(l1, l2) -> {
//							var l = new ArrayList<String>();
//							l.addAll(l1);
//							l.addAll(l2);
//							return l;
//						}));
			
			request.setAttribute("errors", errors.stream()
				.collect(Collectors.groupingBy(
						cv -> cv.getPropertyPath().toString(),
						Collectors.mapping(cv -> cv.getMessage(), Collectors.toList()))));
			
			request.setAttribute("editedUser", u);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/signup.jsp").forward(request, response);
		}
	}

}
