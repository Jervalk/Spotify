package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.models.UserListener;
import fr.spotify_en_mieux_core.models.UserProducer;
import fr.spotify_en_mieux_core.repositories.UserRepository;
import fr.spotify_en_mieux_core.services.ArtistService;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/artists/delete")
public class ArtistsDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArtistService as = new ArtistService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve which user to delete
		int id = Integer.parseInt(request.getParameter("id"));
		// delete user from DB
		as.deleteById(id);
		// redirect to users list
		response.sendRedirect(request.getContextPath() + "/artists");
	}


}
