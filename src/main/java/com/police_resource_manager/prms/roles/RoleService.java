package com.police_resource_manager.prms.roles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.police_resource_manager.prms.formation.Formation;

@Service
@Transactional
public class RoleService {
	
	@Autowired
	private RoleDAO roleRepo;
	
	
	public Role getRoleByName(String roleName) {
		return roleRepo.getRoleByRoleName(roleName);
	}
	
	public List<Formation> getAllRolesByRoleNames(List<String> roleNames){
		List<Formation> roles = new ArrayList<Formation>();
		roleNames.forEach((roleName)->{
			if(this.getRoleByName(roleName)!= null) {
				roles.add(this.getRoleByName(roleName).getFormation());
			}
		});
		return roles;
	}
	
	
}
