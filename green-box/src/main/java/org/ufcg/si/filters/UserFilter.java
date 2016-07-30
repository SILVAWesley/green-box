package org.ufcg.si.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;
import org.ufcg.si.util.ServerConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class UserFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String header = httpRequest.getHeader("Authorization");
		
		if (header == null || !header.startsWith("Bearer ")) {
			throw new ServletException("Token doesn't exist.");
		}
		
		String token = header.substring(7);
		
		try {
			Jwts.parser().setSigningKey(ServerConstants.TOKEN_KEY).parseClaimsJws(token).getBody();
		} catch(SignatureException e) {
			throw new ServletException("Invalid token.");
		}
		
		chain.doFilter(request, response);
	}

}
