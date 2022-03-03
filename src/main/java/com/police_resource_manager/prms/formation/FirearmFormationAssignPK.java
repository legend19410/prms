package com.police_resource_manager.prms.formation;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class FirearmFormationAssignPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="serial_no")
	String serialNo;
	
	@Column(name="form_code")
	String formCode;
	
	@Column(name="date_assigned")
	LocalDateTime dateAssigned;


	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public LocalDateTime getDateAssigned() {
		return dateAssigned;
	}

	public void setDateAssigned(LocalDateTime dateAssigned) {
		this.dateAssigned = dateAssigned;
	}

}