package com.police_resource_manager.prms.vehicles;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;




public interface VehicleDAO extends CrudRepository<Vehicle, String>{
	
	@Query(value="SELECT * FROM vehicle WHERE license_plate LIKE %:query% ", nativeQuery=true)
	List<Vehicle> search(@Param("query") String query);

}
