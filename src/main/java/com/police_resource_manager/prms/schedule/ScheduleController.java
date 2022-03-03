package com.police_resource_manager.prms.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScheduleController {
	
	@Autowired
	private ScheduleRepository scheduleRepo;
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	@Autowired
	private ScheduleService ScheduleService;
	
	@PostMapping("/weekShedule")
	public FullWeekSchedule getWeeksSchedule(@RequestBody FullWeekSchedule weekSchedule) {
		//get schedule
//		System.out.println(weekSchedule);
		FullWeekSchedule fws= new FullWeekSchedule();
		List<OfficerWeekSchedule> ows = ScheduleService.getWeekSchedule(weekSchedule.schedule);
		fws.schedule = ows;
		return fws;
	}
	
	@PostMapping("/submitSchedule")
	public FullWeekSchedule submitSchedule(@RequestBody FullWeekSchedule weekSchedule) {
		//get schedule
//		System.out.println(weekSchedule);
		FullWeekSchedule fws= new FullWeekSchedule();
		List<OfficerWeekSchedule> ows = ScheduleService.submitSchedule(weekSchedule.schedule);
		fws.schedule = ows;
		return fws;
	}
//
	@GetMapping("/schedule")
	@CrossOrigin("http://localhost:3000")
	public Schedule get(@RequestParam(name="officer_reg_no") int officerRegNo, @RequestParam String datetime){
		return scheduleDAO.get(officerRegNo,datetime);
	}
	
	@PostMapping("/schedule")
	@CrossOrigin("http://localhost:3000")
	public List<Schedule> getScheduleInRange(@RequestParam(name="startDate") String startDate, @RequestParam(name="endDate") String endDate){
		System.out.println(startDate +" START");
		System.out.println(endDate +" END");
		return scheduleDAO.getScheduleInRange(startDate,endDate);
	}
	
	@PostMapping("/schedule/officerSchedule")
	@CrossOrigin("http://localhost:3000")
	public List<Schedule> getScheduleInRange(@RequestParam int officerRegNo, @RequestParam  String startDate, @RequestParam String endDate){
		return scheduleDAO.getOfficerWeeksSchedule(officerRegNo,startDate,endDate);
	}
	
	@GetMapping("/all-schedule")
	public List<Schedule> getAll() {
		return ScheduleService.getAll();
	}
}
