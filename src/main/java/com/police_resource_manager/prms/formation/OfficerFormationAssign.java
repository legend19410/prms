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
import com.police_resource_manager.prms.officers.Officer;

@Entity
@Table(name="officer_formation_assign")
@JsonIgnoreProperties({ "officer", "formation" })
public class OfficerFormationAssign {
	
	@EmbeddedId
	private OfficerFormationAssignPK id;
	
	@ManyToOne
	@MapsId("formCode")
	@JoinColumn(name="form_code")
	private Formation formation;
	
	@ManyToOne
	@MapsId("officerRegNo")
	@JoinColumn(name="officer_reg_no")
	private Officer officer;
	
	@Column(name="date_assign_end")
	private LocalDate dateAssignEnd;
	
	public OfficerFormationAssignPK getId() {
		return id;
	}

	public void setId(OfficerFormationAssignPK id) {
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
	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public LocalDate getDateAssignEnd() {
		return dateAssignEnd;
	}

	public void setDateAssignEnd(LocalDate dateAssignEnd) {
		this.dateAssignEnd = dateAssignEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
}
