package com.police_resource_manager.prms.roles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.police_resource_manager.prms.officers.Officer;


public interface RoleDAO extends CrudRepository<Role, Integer>{

	@Query(value="SELECT * FROM role WHERE type=:name", nativeQuery=true)
	Role getRoleByRoleName( String name);
	
}
