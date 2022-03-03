package com.police_resource_manager.prms.officers;

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
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.roles.RoleService;
import com.police_resource_manager.prms.schedule.Schedule;
import com.police_resource_manager.prms.security.JwtUtil;

@Service
@Transactional
public class OfficerService implements UserDetailsService{
	
	@Autowired
	private OfficerRepository officerRepo;
	
	@Autowired
	private OfficerDAO officerDAO;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Officer officer = officerDAO.getOfficerByUsername(username);
		if(officer == null) {
			throw new UsernameNotFoundException("Officer not found in db");
		}else {
			System.out.println("success");
		}
		Collection<SimpleGrantedAuthority> authorities  =  new ArrayList<SimpleGrantedAuthority>();
//		ObjectMapper mapper = new ObjectMapper();
		officer.getRoles().forEach(role -> {
//			String roleName = "";
//			try {
//				 roleName = mapper.writeValueAsString(role);
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			authorities.add(new SimpleGrantedAuthority(role.getType()));
		});
		return new User(officer.getJCFEmail(), officer.getEmailPassword(), authorities);
		
	}
	
	
	public List<Officer> getAllOfficers(){
		return (List<Officer>) officerDAO.findAll();
	}
	
	public List<Officer> getOfficersByFormation(List<String> roles, String username){
		
		Officer officer = this.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
//		List<Role> roles = officer.getRoles();
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
//		List<Formation> formations = roleService.getAllRolesByRoleNames(roleNames);
		List<Officer> officers = new ArrayList<Officer>();
		
		formations.forEach(formation->{
			officers.addAll(formation.getOfficers(formation.getOfficers()));
		});
		return officers;
	}
	
	public Optional<Officer> getOfficerByRegNo(String regNo) throws IllegalQueryFormatException{
		
		int varifiedRegNo;
		
		try {
			varifiedRegNo = Integer.parseInt(regNo);
		}catch(NumberFormatException e){
			throw new IllegalQueryFormatException("The Regulation Number is invalid");
		}
		return officerDAO.findById(varifiedRegNo);
	}
	
	public Officer getOfficer(String username) {
		return officerDAO.getOfficerByUsername(username);
	}
	
	public List<Officer> queryOfficers(String query, List<String> roles, String username){
		Officer officer = this.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
//		List<Role> roles = officer.getRoles();
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
//		List<Formation> formations = roleService.getAllRolesByRoleNames(roleNames);
		List<Officer> officers = new ArrayList<Officer>();
		
		List<Officer> allMatchedOfficers = officerRepo.search(query);
		
		List<Officer> allOfficerFromFormations = new ArrayList<Officer>();
		
		formations.forEach(formation->{
			allOfficerFromFormations.addAll(formation.getOfficers(formation.getOfficers()));
		});
		
		List<Officer> filteredOfficers = allMatchedOfficers.stream()
				.filter(off -> allOfficerFromFormations.contains(off)).collect(Collectors.toList());
		
		return filteredOfficers;
	}
	
	public String refreshMyToken(String bearerToken, String url) throws InvalidJwtException {
		
		String username = jwtUtil.extractVerifiedTokenOwner(bearerToken);
		UserDetails userDetails = this.loadUserByUsername(username);
		
		String newAccessToken = jwtUtil.createAccessToken(userDetails, url);
		return newAccessToken;
	}
	
	public void saveOfficer(Officer officer) {
		officerRepo.save(officer);
	}
	
	
	public Officer updateOfficer(Integer regNo, Map<String, Object> updatedParams) throws NoOfficerFoundException {
		Officer officer = officerDAO.findById(regNo).orElseThrow(() -> 
			 new NoOfficerFoundException("Officer selected to be updated not found")
		);

		System.out.println(updatedParams);
		updatedParams.forEach((key, value)->{
			
			Field field = ReflectionUtils.findRequiredField(Officer.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, officer, value);
		});
		officerDAO.save(officer);
		return officer;
	}
	
	
	public void deleteOfficer(int regNo) {
		officerRepo.delete(regNo);	
	}
	
	public void generateWeekSchedule() {
		
//		List<Officer> officers= (List<Officer>) officerDAO.findAll();
//		for(each officer o) {
//			List<Schedule> myS = o.getSchedule();
//			for(sunday to sat) {
//				myS.add(date, duty, day)
//			}
//		}
	}
}
