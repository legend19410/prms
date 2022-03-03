package com.police_resource_manager.prms.schedule;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ScheduleDAO extends CrudRepository<Schedule, ScheduleId>{
	
	
	@Query(value="SELECT * FROM Schedule WHERE officer_reg_no=:officerRegNo AND CAST(date AS DATE)= CAST(:datetime AS DATE)", nativeQuery=true)
	Schedule get(@Param("officerRegNo") int officerRegNo, @Param("datetime") String datetime);
	
	@Query(value="SELECT * FROM schedule WHERE date between :startDate and :endDate", nativeQuery=true)
	List<Schedule> getScheduleInRange(@Param("startDate") String startDate, @Param("endDate")String endDate);
	
	@Query(value="FROM Schedule WHERE officer_reg_no=:officerRegNo and date between :startDate and :endDate", nativeQuery=true)
	List<Schedule> getOfficerWeeksSchedule(@Param("officerRegNo")int officerRegNo, @Param("startDate")String startDate, @Param("endDate")String endDate);

	@Modifying
	@Transactional
	@Query(value="INSERT INTO schedule VALUES (:officerRegNo, :date, :duty)", nativeQuery=true)
	void saveSchedule(@Param("officerRegNo")int officerRegNo, @Param("date")String date, @Param("duty")String duty);

}

