package com.police_resource_manager.prms.radios;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.police_resource_manager.prms.formation.RadioFormationAssign;
import com.police_resource_manager.prms.formation.VehicleFormationAssign;


import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="radio")
public class Radio {

	
	@Id
	private String serialNo;

	@NotBlank(message="make cannot be blank")
	private String type;
		
	
	@JsonIgnoreProperties("radios")
	@OneToMany(mappedBy="radio")
	private List<RadioFormationAssign> formationAssignments;
	
	@JsonManagedReference
	public List<RadioFormationAssign> getFormationAssignments() {
		return formationAssignments;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFormationAssignments(List<RadioFormationAssign> formationAssignments) {
		this.formationAssignments = formationAssignments;
	}

	
	
}