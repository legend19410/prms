package com.police_resource_manager.prms.formation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.officers.OfficerService;
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.vehicles.Vehicle;

@Service
public class FormationService {
	
	@Autowired
	private OfficerService officerService;
	
	
	public List<Formation> getVerifiedFormations(List<String> roles, String username) {
				
		Officer officer = officerService.getOfficer(username);
				
		// ensure that roles match, extract roles from officers
		List<Role> filteredRoles = officer.getRoles().stream()
				.filter(role -> roles.contains(role.getType())).collect(Collectors.toList());
				
		List<Formation> formations = new ArrayList<Formation>();
				
		// extact formation from roles
		filteredRoles.forEach(role->{
			formations.add(role.getFormation());
		});
				
		return formations;
	}
}
