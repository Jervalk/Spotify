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

import fr.spotify_en_mieux_core.models.Music;
import fr.spotify_en_mieux_core.models.UserListener;
import fr.spotify_en_mieux_core.services.ArtistService;
import fr.spotify_en_mieux_core.services.FileService;
import fr.spotify_en_mieux_core.services.MusicService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet({"/musics/update"})
@MultipartConfig
public class MusicsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArtistService as = new ArtistService();
	private MusicService ms = new MusicService();
	private FileService fs = new FileService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("music", ms.findById(Integer.parseInt(request.getParameter("id"))).get());
		request.setAttribute("artists", as.findAll());
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/musicsUpdate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve request parameter
		String title = request.getParameter("title");
		String[] artistsIds = request.getParameterValues("artists");
		String[] albumsIds = request.getParameterValues("albums");
		String[] stylesIds = request.getParameterValues("styles");
		Part file = request.getPart("file");
		
		// build music object
		Music m = new Music();
		m.setTitle(title);
		m.setOwner((UserListener) request.getSession().getAttribute("user"));
		m.setArtists(Arrays.stream(artistsIds)
				.map(id -> as.findById(Integer.parseInt(id)).get())
				.collect(Collectors.toSet()));
		// TODO: set albums and styles
		if (file != null && file.getSize() > 0) {
			// TODO: compute duration from input stream
			InputStream is = file.getInputStream();
			String fileExtension = file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."));
			Path fileName = fs.generateUniqueFileName("music-file", fileExtension);
			Files.copy(is, fs.getTmpPath(fileName));
			m.setFile(fileName.toString());
		}
		// validating music
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Music>> errors =  validator.validate(m);
		// if the music object is valid
		if (errors.isEmpty()) {
			// save it to database
			ms.update(m);
			// redirect to music list
			response.sendRedirect(request.getContextPath() + "/musics");
		}
		// if there are validation errors
		else {
			// saving a map with validation error in request attribute (for printing validation errors in JSP)
			request.setAttribute("errors", errors.stream()
				.collect(Collectors.groupingBy(
						cv -> cv.getPropertyPath().toString(),
						Collectors.mapping(cv -> cv.getMessage(), Collectors.toList()))));
			// saving the music object  in request attribute (for filling input in JSP)
			request.setAttribute("music", m);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pages/musicsUpdate.jsp").forward(request, response);
		}
	}

}
