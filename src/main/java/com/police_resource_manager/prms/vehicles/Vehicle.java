package com.police_resource_manager.prms.vehicles;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.police_resource_manager.prms.formation.VehicleFormationAssign;


import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="vehicle")
public class Vehicle {

	
	@Id
	private String licensePlate;

	@NotBlank(message="make cannot be blank")
	@Column(name="make")
	private String make;
	
	@NotBlank(message="model cannot be blank")
	@Column(name="model")
	private String model;
	
	@NotBlank(message="year cannot be blank")
	@Column(name="year")
	private Integer year;
	
	
	@JsonIgnoreProperties("vehicles")
	@OneToMany(mappedBy="vehicle")
	private List<VehicleFormationAssign> formationAssignments;
	
	@JsonManagedReference
	public List<VehicleFormationAssign> getFormationAssignments() {
		return formationAssignments;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setFormationAssignments(List<VehicleFormationAssign> formationAssignments) {
		this.formationAssignments = formationAssignments;
	}




	

	
}