package com.police_resource_manager.prms.schedule;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class ScheduleId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="officer_reg_no")
	Integer OfficerRegNo;
	
	@Column(name="date")
	LocalDateTime date;

	public Integer getOfficerRegNo() {
		return OfficerRegNo;
	}

	public void setOfficerRegNo(Integer officerRegNo) {
		OfficerRegNo = officerRegNo;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
}
