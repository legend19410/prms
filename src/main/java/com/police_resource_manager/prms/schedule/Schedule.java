package com.police_resource_manager.prms.schedule;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.police_resource_manager.prms.officers.Officer;


@Entity
@Table(name="schedule")
@NamedQuery(name="Schedule.findAll", query="SELECT s FROM Schedule s")
public class Schedule implements Serializable{
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", officer=" + officer + ", dutyToPerform=" + dutyToPerform + "]";
	}
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@JsonUnwrapped
	private ScheduleId id;
	
//	@MapsId("date")
//	@Column(name="date")
//	private LocalDateTime date;
//	
	@ManyToOne
	@MapsId("officerRegNo")
	@JoinColumn(name="officer_reg_no")
//	@JsonManagedReference
	private Officer officer;
	
//	@Id
//	@Column(name="officer_reg_no")
//	private int OfficerRegNo;
//	
//	@Id
////	@Temporal(TemporalType.TIMESTAMP)
//	private LocalDateTime date;
	
	
	@Column(name="duty_to_perform")
	private String dutyToPerform;
	
//	public int getOfficerRegNo() {
//		return OfficerRegNo;
//	}
//	public void setOfficerRegNo(int officerRegNo) {
//		OfficerRegNo = officerRegNo;
//	}
//	public LocalDateTime getDate() {
//		return date;
//	}
//	public void setDate(LocalDateTime date) {
//		this.date = date;
//	}
	
	@JsonBackReference
	public ScheduleId getScheduleId() {
		return id;
	}
	
	@JsonBackReference
	public Officer getOfficer() {
		return officer;
	}
	
	public String getDutyToPerform() {
		return dutyToPerform;
	}
	public void setDutyToPerform(String dutyToPerform) {
		this.dutyToPerform = dutyToPerform;
	}
	

}
