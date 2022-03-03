package com.police_resource_manager.prms.formation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.police_resource_manager.prms.firearms.Firearm;
import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.radios.Radio;
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.vehicles.Vehicle;

@Entity
@JsonIgnoreProperties({ "officers","roles","vehicles","radios","firearms"})
public class Formation {

	@Id	
	@Column(name="form_code")
	private String formCode;
	
	@Column(name="form_name")
	private String formName;
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(mappedBy="formation")
	private List<Role> roles;
	
//	@JsonIgnoreProperties("formationAssignments")
	@OneToMany(mappedBy="formation")
	private List<OfficerFormationAssign> officers; 
	
	@OneToMany(mappedBy="formation")
	private List<VehicleFormationAssign> vehicles; 

	@OneToMany(mappedBy="formation")
	private List<RadioFormationAssign> radios; 
	
	@OneToMany(mappedBy="formation")
	private List<FirearmFormationAssign> firearms; 

	@JsonManagedReference
	public List<OfficerFormationAssign> getOfficers() {
		return officers;
	}
	
	@JsonManagedReference
	public List<VehicleFormationAssign> getVehicles() {
		return vehicles;
	}
	
	public List<Officer> getOfficers(List<OfficerFormationAssign> officerForm){
		List<Officer> officers = new ArrayList<Officer>();
		officerForm.forEach(officer->{
			officers.add(officer.getOfficer());
		});
		return officers;
	}
	
	public List<Vehicle> getVehicles(List<VehicleFormationAssign> vehicleFormation){
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicleFormation.forEach(vehicle->{
			vehicles.add(vehicle.getVehicle());
		});
		return vehicles;
	}
	
	
	public List<Radio> getRadios(List<RadioFormationAssign> radioFormation){
		List<Radio> radios = new ArrayList<Radio>();
		radioFormation.forEach(radio->{
			radios.add(radio.getRadio());
		});
		return radios;
	}
	
	public List<Firearm> getFirearms(List<FirearmFormationAssign> firearmFormation){
		List<Firearm> firearms = new ArrayList<Firearm>();
		firearmFormation.forEach(firearm->{
			firearms.add(firearm.getFirearm());
		});
		return firearms;
	}


	public void setOfficers(List<OfficerFormationAssign> officers) {
		this.officers = officers;
	}
	
	public void setVehicles(List<VehicleFormationAssign> vehicles) {
		this.vehicles = vehicles;
	}
	
	public List<RadioFormationAssign> getRadios() {
		return radios;
	}
	
	public List<FirearmFormationAssign> getFirearms() {
		return firearms;
	}

	public void setRadios(List<RadioFormationAssign> radios) {
		this.radios = radios;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
}
