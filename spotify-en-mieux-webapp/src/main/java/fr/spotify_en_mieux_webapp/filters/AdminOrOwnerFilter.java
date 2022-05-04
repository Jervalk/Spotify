package fr.spotify_en_mieux_webapp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.spotify_en_mieux_core.models.Content;
import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.repositories.ContentRepository;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter({ "/*/update", "/*/delete" })
public class AdminOrOwnerFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		User user = (User) req.getSession().getAttribute("user");
		ContentRepository cr = new ContentRepository();
		Content content =  cr.findById(Integer.parseInt(req.getParameter("id"))).get();
		
		if (user == null ||
			!(user.getRole().equals("ROLE_ADMIN") || user.equals(content.getOwner()))) {
			res.sendRedirect(req.getContextPath() + "/");
		} else {
			chain.doFilter(request, response);
		}
	}

}
