package com.police_resource_manager.prms.schedule;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.officers.OfficerDAO;


@Repository
public class ScheduleRepository {
	
	@Autowired
	private EntityManager entityManager;


	public List<Schedule> get() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Schedule> query = currentSession.createQuery("from Schedule", Schedule.class);
		List<Schedule> list = query.getResultList();
		return list;
				
	}

	
	public Schedule get(int officerRegNo, String datetime) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query q = currentSession.createQuery("FROM Schedule WHERE officer_reg_no=:officerRegNo AND date='"+datetime+"'");
		q.setParameter("officerRegNo", officerRegNo);
//		q.setParameter("datetime", datetime);
		Schedule schedule = (Schedule) q.uniqueResult();
//		Schedule schedule = currentSession.get(Schedule.class, officerRegNo, date);
		return schedule;
	}
	
	
	public List<Schedule> getScheduleInRange(String startDate, String endDate) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query q = currentSession.createQuery("FROM Schedule WHERE date between '"+ startDate+"' and '"+endDate+"'");
//		q.setParameter("datetime", datetime);
		List<Schedule> schedule = (List<Schedule>) q.list();
//		Schedule schedule = currentSession.get(Schedule.class, officerRegNo, date);
		return schedule;
	}
//	
	
	public List<Schedule> getOfficerWeeksSchedule(int officerRegNo, String startDate, String endDate) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query q = currentSession.createQuery("FROM Schedule WHERE officer_reg_no="+officerRegNo+" and date between '"+ startDate+"' and '"+endDate+"'");
//		q.setParameter("datetime", datetime);
		List<Schedule> schedule = (List<Schedule>) q.list();
//		Schedule schedule = currentSession.get(Schedule.class, officerRegNo, date);
		return schedule;
	}


	
	public void save(Schedule schedule) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(schedule);
	}

//	@Override
//	public void delete(int id) {
//		Session currentSession = entityManager.unwrap(Session.class);
//		Officer officer = currentSession.get(Officer.class, id);
//		currentSession.delete(officer);
//		
//	}
 

}
