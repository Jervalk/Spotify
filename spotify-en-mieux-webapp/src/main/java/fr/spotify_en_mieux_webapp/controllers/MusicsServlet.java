package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.services.MusicService;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/musics")
public class MusicsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MusicService ms = new MusicService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve users from DB and save them as request attribute
		request.setAttribute("musics", ms.findAll());
		// forward to jsp
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/musicsList.jsp").forward(request, response);
	}


}
