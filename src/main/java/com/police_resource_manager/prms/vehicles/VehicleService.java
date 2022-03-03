package com.police_resource_manager.prms.vehicles;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police_resource_manager.prms.exceptions.ApiRequestException;
import com.police_resource_manager.prms.exceptions.IllegalQueryFormatException;
import com.police_resource_manager.prms.exceptions.InvalidJwtException;
import com.police_resource_manager.prms.exceptions.NoOfficerFoundException;
import com.police_resource_manager.prms.formation.Formation;
import com.police_resource_manager.prms.formation.FormationService;
import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.officers.OfficerService;
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.roles.RoleService;
import com.police_resource_manager.prms.schedule.Schedule;
import com.police_resource_manager.prms.security.JwtUtil;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class VehicleService{
	
	
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private OfficerVehicleAssignDAO officerVehicleAssignDAO;
	
	@Autowired
	private OfficerService officerService;
	
	@Autowired
	private FormationService formationService;

	
	public List<Vehicle> getAllVehicles(){
		return (List<Vehicle>) vehicleDAO.findAll();
	}
	
	public Page<OfficerVehicleAssign> getOfficerVehicleAssignments(Pageable page){
		return (Page<OfficerVehicleAssign>) officerVehicleAssignDAO.findAll(page);
	}
	
	public List<Vehicle> getVehiclesByFormation(List<String> roles, String username){
		
		List<Formation> verifiedFormations = formationService.getVerifiedFormations(roles,username);
		
		
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		// extract all vehicles from given formations
		verifiedFormations.forEach(formation->{
			vehicles.addAll(formation.getVehicles(formation.getVehicles()));
		});
		return vehicles;
	}
	
	public Optional<Vehicle> getVehicle(String plate) {
		
		return vehicleDAO.findById(plate);
	}
	
	
	public List<Vehicle> queryVehicles(String query, List<String> roles, String username){
		Officer officer = officerService.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
//		List<Role> roles = officer.getRoles();
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
//		List<Formation> formations = roleService.getAllRolesByRoleNames(roleNames);
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		List<Vehicle> allMatchedVehicles = vehicleDAO.search(query);
		
		List<Vehicle> allVehiclesFromFormations = new ArrayList<Vehicle>();
		
		formations.forEach(formation->{
			allVehiclesFromFormations.addAll(formation.getVehicles(formation.getVehicles()));
		});
		
		List<Vehicle> filteredVehicles = allMatchedVehicles.stream()
				.filter(veh -> allVehiclesFromFormations.contains(veh)).collect(Collectors.toList());
		
		return filteredVehicles;
	}
	
	
	public void saveVehicle(Vehicle vehicle) {
		vehicleDAO.save(vehicle);
	}
	
	
	public Vehicle updateVehicle(String licensePlate, Map<String, Object> updatedParams) throws NoOfficerFoundException {
		Vehicle vehicle = vehicleDAO.findById(licensePlate).orElseThrow(() -> 
			 new NoOfficerFoundException("Officer selected to be updated not found")
		);

		updatedParams.forEach((key, value)->{
			
			Field field = ReflectionUtils.findRequiredField(Vehicle.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, vehicle, value);
		});
		vehicleDAO.save(vehicle);
		return vehicle;
	}
	
	
	public void deleteVehicle(String licensePlate) {
		vehicleDAO.deleteById(licensePlate);	
	}
	

}
