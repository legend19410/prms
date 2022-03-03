package com.police_resource_manager.prms.firearms;

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
public class FirearmController {
	

	@Autowired
	private FirearmService firearmService;
	
//	@GetMapping("/radios")
//	public List<Radio> getRadios() {
//		return radioService.getAllRadios();
//		
//	}
//	
	@GetMapping("/firearms")
	public ResponseEntity<List<Firearm>> getRadios(@RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException{
		
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;

		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		
		List<Firearm> allFirearms = firearmService.getFirearmsByFormation(roles, username);
		return ResponseEntity.ok(allFirearms);
	}
//	
	@GetMapping("firearm/search")
	public List<Firearm> getRadios(@RequestParam String query, @RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;

		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		return firearmService.queryFirearms(query, roles, username);
	}
	
	
	@GetMapping("firearm/{serialNo}")
	public ResponseEntity<Optional<Firearm>> getFirearm(@PathVariable String serialNo) {
		Optional<Firearm> firearm = firearmService.getFirearm(serialNo);
		return ResponseEntity.ok(firearm);
	}
	
	@PostMapping("/firearm")
	public Firearm save(@Valid @RequestBody Firearm firearm){
		firearmService.saveFirearm(firearm);
		return firearm;
	}
	
	
	@PatchMapping("/firearm/{serialNo}")
	public Firearm updateFirearm(@PathVariable String serialNo, @RequestBody Map<String, Object> updatedParams) throws NoOfficerFoundException{
		return firearmService.updateFirearm(serialNo, updatedParams);
	}
	
	@DeleteMapping("/firearm/{serialNo}")
	public String delete(@PathVariable String serialNo){
		firearmService.deleteRadio(serialNo);
		return "firearm deleted"; 
	}
	

}
