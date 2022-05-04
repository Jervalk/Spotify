package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.spotify_en_mieux_core.models.Artist;
import fr.spotify_en_mieux_core.models.Group;
import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.models.UserListener;
import fr.spotify_en_mieux_core.services.ArtistService;
import fr.spotify_en_mieux_core.services.FileService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet({"/artists/add"})
@MultipartConfig
public class ArtistsAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArtistService as = new ArtistService();
	private FileService fs = new FileService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("artists", as.findAll());
		request.setAttribute("groups", as.findAllGroups());
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/artistsAdd.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve request parameter
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		Part picture = request.getPart("picture");
		String[] membersIds = request.getParameterValues("members");
		String[] groupsIds = request.getParameterValues("groups");
		// build an Artist object
		Artist a;
		switch (type) {
		case "PERSON":
			// build an Artist object
			a = new Artist();
			if (groupsIds != null)
				a.setGroups(Arrays.stream(groupsIds)
					.map(id -> ((Group) as.findById(Integer.parseInt(id)).get()))
					.collect(Collectors.toSet()));
			break;
		case "GROUP":
			// build an Artist object
			a = new Group();
			if (membersIds != null)
				((Group)a).setMembers(Arrays.stream(membersIds)
					.map(id -> as.findById(Integer.parseInt(id)).get())
					.collect(Collectors.toSet()));
			break;
		default:
			throw new RuntimeException("invalid artist type");
		}
		a.setName(name);
		if (((User) request.getSession().getAttribute("user")).getRole().equals("ROLE_PRODUCER"))
			a.setOwner((UserListener) request.getSession().getAttribute("user"));
		if (picture != null && picture.getSize() > 0) {
			InputStream pis = picture.getInputStream();
			String pictureExtension = picture.getSubmittedFileName().substring(picture.getSubmittedFileName().lastIndexOf("."));
			Path fileName = fs.generateUniqueFileName("artist-picture", pictureExtension);
			Files.copy(pis, fs.getTmpPath(fileName));
			a.setPicture(fileName.toString());
		}
		// validating artist
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Artist>> errors =  validator.validate(a);
		// if the music object is valid
		if (errors.isEmpty()) {
			// save object to DB
			as.save(a);
			// redirect to artists list
			response.sendRedirect(request.getContextPath() + "/artists");
		}
		// if there are validation errors
		else {
			// saving a map with validation error in request attribute (for printing validation errors in JSP)
			request.setAttribute("errors", errors.stream()
				.collect(Collectors.groupingBy(
					cv -> cv.getPropertyPath().toString(),
					Collectors.mapping(cv -> cv.getMessage(), Collectors.toList()))));
			// saving the music object  in request attribute (for filling input in JSP)
			request.setAttribute("artist", a);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/artistsAdd.jsp").forward(request, response);
		}
	}

}
