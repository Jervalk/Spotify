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
@WebFilter({ "/logout", "/signout" })
public class ConnectedFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getSession().getAttribute("user") == null) {
			res.sendRedirect(req.getContextPath() + "/signin");
		} else {
			chain.doFilter(request, response);
		}
	}

}
