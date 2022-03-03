package com.police_resource_manager.prms.formation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.police_resource_manager.prms.schedule.Schedule;
import com.police_resource_manager.prms.schedule.ScheduleDAO;



@RestController
@Transactional
@RequestMapping("/api")
public class FormationController {

	@Autowired
	private FormationDAO formationDAO;
	
	@GetMapping("/formation")
	@CrossOrigin("http://localhost:3000")
	public List<Formation> get(){
		List<Formation> f = (List<Formation>) formationDAO.findAll();
		return f ;
	}
	
	
}
