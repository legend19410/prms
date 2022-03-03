package com.police_resource_manager.prms.vehicles;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.police_resource_manager.prms.officers.Officer;


@Entity
@Table(name="officer_vehicle_assign")
@JsonIgnoreProperties({ "vehicle", "officer" })
public class OfficerVehicleAssign {
	
	@EmbeddedId
	private OfficerVehicleAssignPK id;
	
	@ManyToOne
	@MapsId("regNo")
	@JoinColumn(name="reg_no")
	private Officer officer;
	
	@ManyToOne
	@MapsId("licensePlate")
	@JoinColumn(name="license_plate")
	private Vehicle vehicle;
	
	@Column(name="datetime_assign_end")
	private LocalDate datetimeAssignEnd;
	
	private String status;

	public OfficerVehicleAssignPK getId() {
		return id;
	}

	public void setId(OfficerVehicleAssignPK id) {
		this.id = id;
	}

	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public LocalDate getDatetimeAssignEnd() {
		return datetimeAssignEnd;
	}

	public void setDatetimeAssignEnd(LocalDate datetimeAssignEnd) {
		this.datetimeAssignEnd = datetimeAssignEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	


}
