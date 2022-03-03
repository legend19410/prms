package com.police_resource_manager.prms.vehicles;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OfficerVehicleAssignPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="reg_no")
	Integer regNo;
	
	@Column(name="license_plate")
	String licensePlate;
	
	@Column(name="datetime_assigned")
	LocalDateTime datetimeAssigned;

	public Integer getRegNo() {
		return regNo;
	}

	public void setRegNo(Integer regNo) {
		this.regNo = regNo;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public LocalDateTime getDatetimeAssigned() {
		return datetimeAssigned;
	}

	public void setDateAssigned(LocalDateTime datetimeAssigned) {
		this.datetimeAssigned = datetimeAssigned;
	}


}