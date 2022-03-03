package com.police_resource_manager.prms.officers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.police_resource_manager.prms.roles.Role;


public interface OfficerDAO extends CrudRepository<Officer, Integer>{
	
	@Query(value="SELECT * FROM officer WHERE jcf_email=:username", nativeQuery=true)
	Officer getOfficerByUsername( String username);
	
	@Query(value="SELECT * FROM officer WHERE first_name LIKE %:query% OR last_name LIKE %:query%", nativeQuery=true)
	List<Officer> search(@Param("query") String query);

//	List<Officer> get();
//	
//	List<Officer> search(String officerQuery);
//	
//	Officer get(int id);
//	
//	void save(Officer officer);
//	
//	void update(Officer officer);
//	
//	void delete(int id);
//
//	Officer getOfficerByUsername(String email);
}
