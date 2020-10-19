package br.com.aisdigital.araujofelipe.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.aisdigital.araujofelipe.api.service.CustomUserDetailsService;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	UserDetails userDetails;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader  = request.getHeader("Authorization");
			
		if(authorizationHeader == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Access to staging site\"");
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
			response.getOutputStream().print("buuuu! this is a ghost! please, send a basic auth header. :D");
			return;
		}
		
		String token = authorizationHeader.substring("Basic".length()).trim();
		byte[] decodeToken = Base64.getDecoder().decode(token);
		String userPsw = new String(decodeToken, StandardCharsets.UTF_8);
		final String[] values = userPsw.split(":", 2);
		
		if(values[0].isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		userDetails = userDetailsService.loadUserByUsername(values[0]);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		filterChain.doFilter(request, response);
	}
		
}
