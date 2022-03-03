package com.police_resource_manager.prms.radios;

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
public class RadioService{
	
	
	@Autowired
	private RadioDAO radioDAO;
	
	@Autowired
	private OfficerService officerService;

	
	public List<Radio> getAllRadios(){
		return (List<Radio>) radioDAO.findAll();
	}
	
	public List<Radio> getRadiosByFormation(List<String> roles, String username){
		
		Officer officer = officerService.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
		List<Radio> radios = new ArrayList<Radio>();
		
		formations.forEach(formation->{
			radios.addAll(formation.getRadios(formation.getRadios()));
		});
		return radios;
	}
	
	public Optional<Radio> getRadio(String serialNo) {
		
		return radioDAO.findById(serialNo);
	}
	
	
	public List<Radio> queryRadios(String query, List<String> roles, String username){
		Officer officer = officerService.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
//		List<Role> roles = officer.getRoles();
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
//		List<Formation> formations = roleService.getAllRolesByRoleNames(roleNames);
		List<Radio> radios = new ArrayList<Radio>();
		
		List<Radio> allMatchedRadios = radioDAO.search(query);
		
		List<Radio> allRadiosFromFormations = new ArrayList<Radio>();
		
		formations.forEach(formation->{
			allRadiosFromFormations.addAll(formation.getRadios(formation.getRadios()));
		});
		
		List<Radio> filteredRadios = allMatchedRadios.stream()
				.filter(rad -> allRadiosFromFormations.contains(rad)).collect(Collectors.toList());
		
		return filteredRadios;
	}
	
	
	public void saveRadio(Radio radio) {
		radioDAO.save(radio);
	}
	
	
	public Radio updateRadio(String serialNo, Map<String, Object> updatedParams) throws NoOfficerFoundException {
		Radio radio = radioDAO.findById(serialNo).orElseThrow(() -> 
			 new NoOfficerFoundException("radio selected to be updated not found")
		);

		updatedParams.forEach((key, value)->{
			
			Field field = ReflectionUtils.findRequiredField(Radio.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, radio, value);
		});
		radioDAO.save(radio);
		return radio;
	}
	
	
	public void deleteRadio(String serialNo) {
		radioDAO.deleteById(serialNo);	
	}
	

}
