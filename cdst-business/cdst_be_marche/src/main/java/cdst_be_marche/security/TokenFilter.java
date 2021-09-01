package cdst_be_marche.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import cdst_be_marche.service.JWTsService;

@Component
public class TokenFilter extends GenericFilterBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

	@Autowired
	JWTsService jwtutils;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		LOGGER.info("TokenFilter...");

		String urls = getFilterConfig().getInitParameter("tokenFilterExcludeUrls");
		List<String> tokenFilterExcludeUrls = Arrays.asList(urls.split(","));

		String path = ((HttpServletRequest) request).getServletPath();

		boolean doFilter = true;
		if (!tokenFilterExcludeUrls.contains(path)) {
			boolean isvalid = false;
			try {
				isvalid = jwtutils.checkToken(request);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

			LOGGER.info("Token valido: " + isvalid);

			if (!isvalid) {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"The token is not valid.");
				doFilter = false;
			}
		}
		if (doFilter) chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
