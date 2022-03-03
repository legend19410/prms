package com.police_resource_manager.prms.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	public List<OfficerWeekSchedule> getWeekSchedule(List<OfficerWeekSchedule> weekSchedule) {
		List<OfficerWeekSchedule> ws = new ArrayList<OfficerWeekSchedule>();
		weekSchedule.forEach(officerSchedule->{
			List<ScheduleItem> schLst = new ArrayList<ScheduleItem>();
			officerSchedule.weekDays.forEach(day->{
				Schedule s = getSchedule(officerSchedule.regNo, day.date);
				ScheduleItem si = new ScheduleItem(); 
				if(s == null) {
					si.regNo = officerSchedule.regNo;
					si.date = day.date;
				}else {
					si.regNo = s.getScheduleId().getOfficerRegNo();
					si.date = s.getScheduleId().getDate().toString();
					si.duty = s.getDutyToPerform();
				}
				schLst.add(si);
				
			});
			OfficerWeekSchedule ws2 = new OfficerWeekSchedule();
			ws2.regNo = officerSchedule.regNo;
			ws2.firstName = officerSchedule.firstName;
			ws2.lastName = officerSchedule.lastName;
			ws2.rank = officerSchedule.rank;
			ws2.weekDays = schLst;
			ws.add(ws2);
		});
		return ws;
	}
	
	public  String convertToNewFormat(String dateStr) throws ParseException {
	    TimeZone utc = TimeZone.getTimeZone("UTC");
	    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sourceFormat.setTimeZone(utc);
	    Date convertedDate = sourceFormat.parse(dateStr);
	    return destFormat.format(convertedDate);
	}
	
	public List<OfficerWeekSchedule> submitSchedule(List<OfficerWeekSchedule> weekSchedule){
		weekSchedule.forEach(officerSchedule->{
			officerSchedule.weekDays.forEach(day->{
				if(day.duty != null) {
					
					try {
						scheduleDAO.saveSchedule(day.regNo, this.convertToNewFormat(day.date),day.duty);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		});
		return weekSchedule;	
	}
	
	public Schedule getSchedule(Integer officerRegNo, String datetime){
		return scheduleDAO.get(officerRegNo, datetime);
	}
	
	public List<Schedule> getAll(){
		return (List<Schedule>) scheduleDAO.findAll();
	}

}
