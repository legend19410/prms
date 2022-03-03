package com.police_resource_manager.prms.officers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.police_resource_manager.prms.exceptions.IllegalQueryFormatException;
import com.police_resource_manager.prms.exceptions.InvalidJwtException;
import com.police_resource_manager.prms.exceptions.NoOfficerFoundException;
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.security.AuthenticationResponse;
import com.police_resource_manager.prms.security.DecodedToken;



@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class OfficerController {
	

	@Autowired
	private OfficerService officerService;
	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
	

	@GetMapping("/is-authenticated")
	public String isAuthenticated() {
		return "yes";
	}
	
	
	@GetMapping("/officers")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<List<Officer>> getOfficer(@RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException{
		
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;

		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		
		List<Officer> allOfficers = officerService.getOfficersByFormation(roles, username);
		return ResponseEntity.ok(allOfficers);
	}
	
	@GetMapping("officer/search")
	public List<Officer> getOfficers(@RequestParam String query, @RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;

		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		return officerService.queryOfficers(query, roles, username);
	}
	
	
	@GetMapping("officer/{regNo}")
	public ResponseEntity<Optional<Officer>> getOfficerByRegNo(@PathVariable String regNo) throws IllegalQueryFormatException {
		Optional<Officer> officer = officerService.getOfficerByRegNo(regNo);
		return ResponseEntity.ok(officer);
	}
	
	@PostMapping("/officer")
	public Officer save(@Valid @RequestBody Officer officer){
		officerService.saveOfficer(officer);
		return officer;
	}
	
	
	@PatchMapping("/officer/{regNo}")
	public Officer updateOfficer(@PathVariable Integer regNo, @RequestBody Map<String, Object> updatedParams) throws NoOfficerFoundException{
		return officerService.updateOfficer(regNo, updatedParams);
	}
	
	@DeleteMapping("/officer/{regNo}")
	public String delete(@PathVariable int regNo){
		officerService.deleteOfficer(regNo);
		return "officer deleted"; 
	}
	
	
	
	@GetMapping("officer/username/{username}")
	public Officer get(@PathVariable String username, @RequestHeader (name="Authorization") String token) {
		return officerService.getOfficer(username);
	}
	
	@GetMapping("user")
	public Officer get(@RequestHeader (name="Authorization") String encodedtoken) throws UnsupportedEncodingException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;
		return officerService.getOfficer(username);
	}

	@GetMapping("/officer/refresh-token")
	public AuthenticationResponse refreshMyToken(HttpServletRequest request, HttpServletResponse response) throws InvalidJwtException{
		String bearerToken = request.getHeader("Authorization");
		String url = request.getRequestURL().toString();
		String newAccessToken = officerService.refreshMyToken(bearerToken, url);
		String refreshToken = bearerToken.substring("Bearer ".length());
		return new AuthenticationResponse(newAccessToken, refreshToken);
	}

}
