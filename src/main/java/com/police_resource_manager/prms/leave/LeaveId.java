package com.police_resource_manager.prms.leave;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class LeaveId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="officer_reg_no")
	Integer officerRegNo;
	
	@Column(name="start_date")
	LocalDate startDate;
	
	String type;

	public Integer getOfficerRegNo() {
		return officerRegNo;
	}

	public void setOfficerRegNo(Integer officerRegNo) {
		this.officerRegNo = officerRegNo;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}