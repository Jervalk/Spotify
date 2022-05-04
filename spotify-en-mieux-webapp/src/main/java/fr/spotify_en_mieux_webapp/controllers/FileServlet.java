package fr.spotify_en_mieux_webapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.utils.PropertiesUtil;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet({"/files/*"})
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String directory = PropertiesUtil.getProperty("spotify.filepath");
    	// recuperation du nom de fichier dans l'URL 
    	String fileName = request.getPathInfo().substring(1);
    	// ouverture d'un InputStream et d'un outputStream
    	try (InputStream is = Files.newInputStream(Paths.get(directory + fileName));
    		 OutputStream os = response.getOutputStream()) {
    		// envoie de l'image
    		is.transferTo(os);
    	}
    }


}
