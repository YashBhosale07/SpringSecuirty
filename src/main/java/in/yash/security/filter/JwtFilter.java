package in.yash.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.yash.security.jwtService.JwtService;
import in.yash.security.service.impl.CustomUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserService customUserService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader=request.getHeader("Authorization");
		String jwtToken=null;
		String username=null;
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken=authorizationHeader.substring(7);
			username=jwtService.extractUsername(jwtToken);
		}
		if(username!=null) {
			UserDetails details=customUserService.loadUserByUsername(username);
			if(jwtService.isTokenValid(jwtToken, details)) {
				UsernamePasswordAuthenticationToken authenticationToken=new
						UsernamePasswordAuthenticationToken(username,null, details.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			 
			
		}
		filterChain.doFilter(request, response);
		
	}
	
	
	
	
}
