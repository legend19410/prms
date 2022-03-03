package com.police_resource_manager.prms.formation;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.police_resource_manager.prms.vehicles.Vehicle;

@Entity
@Table(name="vehicle_formation_assign")
@JsonIgnoreProperties({ "officer", "formation" })
public class VehicleFormationAssign {
	
	@EmbeddedId
	private VehicleFormationAssignPK id;
	
	@ManyToOne
	@MapsId("formCode")
	@JoinColumn(name="form_code")
	private Formation formation;
	
	@ManyToOne
	@MapsId("licensePlate")
	@JoinColumn(name="license_plate")
	private Vehicle vehicle;
	
	@Column(name="date_assign_end")
	private LocalDate dateAssignEnd;
	
	private String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public VehicleFormationAssignPK getId() {
		return id;
	}

	public void setId(VehicleFormationAssignPK id) {
		this.id = id;
	}

	@JsonBackReference
	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	@JsonManagedReference
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public LocalDate getDateAssignEnd() {
		return dateAssignEnd;
	}

	public void setDateAssignEnd(LocalDate dateAssignEnd) {
		this.dateAssignEnd = dateAssignEnd;
	}

}
