package com.police_resource_manager.prms.firearms;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;




public interface FirearmDAO extends CrudRepository<Firearm, String>{
	
	@Query(value="SELECT * FROM firearm WHERE serial_no LIKE %:query% ", nativeQuery=true)
	List<Firearm> search(@Param("query") String query);

}
