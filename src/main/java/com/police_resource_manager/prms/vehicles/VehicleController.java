package com.police_resource_manager.prms.vehicles;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class VehicleController {
	

	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping("/vehicles")
	public List<Vehicle> getVehicles() {
		return vehicleService.getAllVehicles();
		
	}
	
	  @GetMapping("/officer-vehicle-assignments")
	  Page<OfficerVehicleAssign> getOfficerVehicleAssignments(Pageable page) {
	    return vehicleService.getOfficerVehicleAssignments(page);
	  }
	
	
//	@GetMapping("/vehicles")
//	public ResponseEntity<List<Vehicle>> getVehicles(@RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException{
//		
//		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
//		String username = decodedToken.sub;
//
//		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
//		
//		List<Vehicle> allVehicles = vehicleService.getVehiclesByFormation(roles, username);
//		return ResponseEntity.ok(allVehicles);
//	}
//	
	@GetMapping("vehicle/search")
	public List<Vehicle> getOfficers(@RequestParam String query, @RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;

		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		return vehicleService.queryVehicles(query, roles, username);
	}
	
	
	@GetMapping("vehicle/{plate}")
	public ResponseEntity<Optional<Vehicle>> getOfficerByRegNo(@PathVariable String plate) {
		Optional<Vehicle> vehicle = vehicleService.getVehicle(plate);
		return ResponseEntity.ok(vehicle);
	}
	
	@PostMapping("/vehicle")
	public Vehicle save(@Valid @RequestBody Vehicle vehicle){
		vehicleService.saveVehicle(vehicle);
		return vehicle;
	}
	
	
	@PatchMapping("/vehicle/{plate}")
	public Vehicle updateVehicle(@PathVariable String plate, @RequestBody Map<String, Object> updatedParams) throws NoOfficerFoundException{
		return vehicleService.updateVehicle(plate, updatedParams);
	}
	
	@DeleteMapping("/vehicle/{plate}")
	public String delete(@PathVariable String plate){
		vehicleService.deleteVehicle(plate);
		return "vehicle deleted"; 
	}
	

}
