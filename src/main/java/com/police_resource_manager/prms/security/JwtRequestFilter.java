package com.police_resource_manager.prms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police_resource_manager.prms.exceptions.InvalidJwtException;
import com.police_resource_manager.prms.officers.OfficerRepository;
import com.police_resource_manager.prms.officers.OfficerService;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private OfficerService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
		throws ServletException, IOException{
		
		if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/officer/refresh-token") || request.getHeader("Authorization") == null) {
			
			filterChain.doFilter(request, response);
			
		}
		else {
			
			String bearerToken = request.getHeader("Authorization");
			
			try {
				
				String jwt = jwtUtil.extractJwt(bearerToken);
				String username = jwtUtil.extractVerifiedTokenOwner(bearerToken);
					
					
				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
					if(jwtUtil.validateToken(jwt, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
				filterChain.doFilter(request, response);
			}catch(ExpiredJwtException exception) {		
				response.sendError(401,"expired");
			}
			catch(InvalidJwtException exception) {
				response.sendError(401,"invalid token");
			}
//			catch(Exception exception) {
////				response.sendError(401, "You are unauthorized to resource requested");	
//				response.sendError(401, exception.getMessage());
//			}
		}
	}

}
