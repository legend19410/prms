package com.police_resource_manager.prms.firearms;

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
import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.officers.OfficerService;
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.roles.RoleService;
import com.police_resource_manager.prms.schedule.Schedule;
import com.police_resource_manager.prms.security.JwtUtil;

@Service
@Transactional
public class FirearmService{
	
	
	@Autowired
	private FirearmDAO firearmDAO;
	
	@Autowired
	private OfficerService officerService;

	
	public List<Firearm> getAllFirearms(){
		return (List<Firearm>) firearmDAO.findAll();
	}
	
	public List<Firearm> getFirearmsByFormation(List<String> roles, String username){
		
		Officer officer = officerService.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
		List<Firearm> firearms = new ArrayList<Firearm>();
		
		formations.forEach(formation->{
			firearms.addAll(formation.getFirearms(formation.getFirearms()));
		});
		return firearms;
	}
	
	public Optional<Firearm> getFirearm(String serialNo) {
		
		return firearmDAO.findById(serialNo);
	}
	
	
	public List<Firearm> queryFirearms(String query, List<String> roles, String username){
		Officer officer = officerService.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
//		List<Role> roles = officer.getRoles();
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
//		List<Formation> formations = roleService.getAllRolesByRoleNames(roleNames);
		List<Firearm> firearms = new ArrayList<Firearm>();
		
		List<Firearm> allMatchedFirearms = firearmDAO.search(query);
		
		List<Firearm> allFirearmsFromFormations = new ArrayList<Firearm>();
		
		formations.forEach(formation->{
			allFirearmsFromFormations.addAll(formation.getFirearms(formation.getFirearms()));
		});
		
		List<Firearm> filteredFirearms = allMatchedFirearms.stream()
				.filter(rad -> allFirearmsFromFormations.contains(rad)).collect(Collectors.toList());
		
		return filteredFirearms;
	}
	
	
	public void saveFirearm(Firearm firearm) {
		firearmDAO.save(firearm);
	}
	
	
	public Firearm updateFirearm(String serialNo, Map<String, Object> updatedParams) throws NoOfficerFoundException {
		Firearm firearm = firearmDAO.findById(serialNo).orElseThrow(() -> 
			 new NoOfficerFoundException("Firearm"
			 		+ "selected to be updated not found")
		);

		updatedParams.forEach((key, value)->{
			
			Field field = ReflectionUtils.findRequiredField(Firearm.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, firearm, value);
		});
		firearmDAO.save(firearm);
		return firearm;
	}
	
	
	public void deleteRadio(String serialNo) {
		firearmDAO.deleteById(serialNo);	
	}
	

}
