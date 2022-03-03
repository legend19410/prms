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
import com.police_resource_manager.prms.firearms.Firearm;
import com.police_resource_manager.prms.radios.Radio;
import com.police_resource_manager.prms.vehicles.Vehicle;

@Entity
@Table(name="firearm_formation_assign")
@JsonIgnoreProperties({ "firearm", "formation" })
public class FirearmFormationAssign {
	
	@EmbeddedId
	private FirearmFormationAssignPK id;
	
	@ManyToOne
	@MapsId("formCode")
	@JoinColumn(name="form_code")
	private Formation formation;
	
	@ManyToOne
	@MapsId("serialNo")
	@JoinColumn(name="serial_no")
	private Firearm firearm;
	
	@Column(name="date_assign_end")
	private LocalDate dateAssignEnd;
	
	private String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public FirearmFormationAssignPK getId() {
		return id;
	}

	public void setId(FirearmFormationAssignPK id) {
		this.id = id;
	}

	@JsonBackReference
	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}


	public Firearm getFirearm() {
		return firearm;
	}

	public void setFirearm(Firearm firearm) {
		this.firearm = firearm;
	}

	public LocalDate getDateAssignEnd() {
		return dateAssignEnd;
	}

	public void setDateAssignEnd(LocalDate dateAssignEnd) {
		this.dateAssignEnd = dateAssignEnd;
	}

}
