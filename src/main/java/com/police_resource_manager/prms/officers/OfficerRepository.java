package com.police_resource_manager.prms.officers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.police_resource_manager.prms.schedule.Schedule;

@Repository
public class OfficerRepository {
	
	@Autowired
	private EntityManager entityManager;

	
	public List<Officer> get() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Officer> query = currentSession.createQuery("from Officer", Officer.class);
		List<Officer> list = query.getResultList();
		return list;		
	}

	
	public Officer get(int regNo) {
		Session currentSession = entityManager.unwrap(Session.class);
		Officer officer = currentSession.get(Officer.class, regNo);
		return officer;
	}
	
	
	public Officer getOfficerByUsername(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query q = currentSession.createQuery("FROM Officer WHERE jcf_email=:username");
		q.setParameter("username", username);
		Officer officer = (Officer) q.uniqueResult();
		return officer;
	}
	
	
	public List<Officer> search(String query) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query q = currentSession.createQuery("FROM Officer WHERE first_name like ?1");
		q.setParameter(1, "%"+query+"%");
		List<Officer> list = (List<Officer>)q.getResultList();
		return list;
	}


	public void save(Officer officer) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(officer);
	}
	
	
	public void update(Officer officer) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(officer);
	}

	
	public void delete(int regNo) {
		Session currentSession = entityManager.unwrap(Session.class);
		Officer officer = currentSession.get(Officer.class, regNo);
		currentSession.delete(officer);
		
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		Officer officer = this.getOfficerByUsername(username);
//		if(officer == null) {
//			throw new UsernameNotFoundException("Officer not found in db");
//		}else {
//			System.out.println("success");
//		}
//		Collection<SimpleGrantedAuthority> authorities  =  new ArrayList<SimpleGrantedAuthority>();
//		officer.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		});
//		return new User(officer.getJCFEmail(), officer.getEmailPassword(), authorities);
//		
//	}
	
//	Session currentSession = entityManager.unwrap(Session.class);
//	Query q = currentSession.createQuery("FROM Schedule WHERE officer_reg_no="+officerRegNo+" and date between '"+ startDate+"' and '"+endDate+"'");
////	q.setParameter("datetime", datetime);
//	List<Schedule> schedule = (List<Schedule>) q.list();
////	Schedule schedule = currentSession.get(Schedule.class, officerRegNo, date);
//	return schedule;
			
 

}
