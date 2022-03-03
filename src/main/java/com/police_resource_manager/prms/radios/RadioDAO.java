package com.police_resource_manager.prms.radios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;




public interface RadioDAO extends CrudRepository<Radio, String>{
	
	@Query(value="SELECT * FROM radio WHERE serial_no LIKE %:query% ", nativeQuery=true)
	List<Radio> search(@Param("query") String query);

}
