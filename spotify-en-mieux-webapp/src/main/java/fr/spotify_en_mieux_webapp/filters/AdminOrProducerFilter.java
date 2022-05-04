package fr.spotify_en_mieux_webapp.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.models.User;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter({ "/musics/add", "/artists/add", "/albums/add" })
public class AdminOrProducerFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		User user = (User) req.getSession().getAttribute("user"); 
		
		if (user == null ||
			!(user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_PRODUCER"))) {
			res.sendRedirect(req.getContextPath() + "/");
		} else {
			chain.doFilter(request, response);
		}
	}

}
