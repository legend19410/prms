package com.police_resource_manager.prms.officers;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.police_resource_manager.prms.roles.Role;

import lombok.Data;


@Embeddable
class OfficerRoleKey implements Serializable{
	@Column(name="role_id")
	Integer roleId;
	
	@Column(name="officer_reg_no")
	Integer officerRegNo;
}

@Entity
@Table(name="officer_role")
@NamedQuery(name="OfficerRole.findAll", query="SELECT o FROM OfficerRole o")
public class OfficerRole{
	
	
	@EmbeddedId
	OfficerRoleKey id;
	
	//bi-directional many-to-one association to Grocery
	@ManyToOne
	@MapsId("officerRegNo")
	@JoinColumn(name="officer_reg_no")
//	@JsonManagedReference
	private Officer officer;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@MapsId("roleId")
	@JoinColumn(name="role_id")
//	@JsonManagedReference
	private Role role;

	@JsonBackReference
	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

//	@JsonBackReference
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
}
