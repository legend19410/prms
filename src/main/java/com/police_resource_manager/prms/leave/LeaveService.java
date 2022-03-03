package com.police_resource_manager.prms.leave;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.police_resource_manager.prms.formation.Formation;
import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.officers.OfficerService;
import com.police_resource_manager.prms.roles.Role;

@Service
public class LeaveService {

	@Autowired
	private LeaveDAO leaveDAO;
	
	@Autowired
	private OfficerService officerService;
	
	public List<Leave> getAllLeave() {
		return (List<Leave>) leaveDAO.findAll();
	
	}
	
	
	
	public List<Leave> getLeavesByFormation(List<String> roles, String username){
		
		Officer officer = officerService.getOfficer(username);
		List<Formation> formations = new ArrayList<Formation>();
		
		List<Role> filteredRoles = officer.getRoles().stream()
			    .filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
		
//		List<Role> roles = officer.getRoles();
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
		
		List<Officer> officers = new ArrayList<Officer>();
		List<Leave> leaves = new ArrayList<Leave>();
		
		formations.forEach(formation->{
			officers.addAll(formation.getOfficers(formation.getOfficers()));
		});
		
		officers.forEach(cop->{
			leaves.addAll(cop.getLeaves());
		});
		
		return leaves;
	}
	
	public Leave getLeaveForOfficer(String type) {
		return null;
	}
	
	public List<Leave> getAllLeaveForOfficer() {
		return null;
	}
}
