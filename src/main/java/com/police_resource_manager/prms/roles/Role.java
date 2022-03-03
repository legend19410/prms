package com.police_resource_manager.prms.roles;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police_resource_manager.prms.formation.Formation;
import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.officers.OfficerRole;


@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String type;
	
	@ManyToOne
//	@JsonBackReference
	@JoinColumn(name="form_code")
	private Formation formation;
	
	public Role(){}

//	@OneToMany(mappedBy="role")
//	@JsonBackReference
//	private List<OfficerRole> officers;
	
//	@ManyToMany(mappedBy="roles")
//	@JsonBackReference
//	private List<Officer> officers;
	
	
	public int getId() {
		return this.id;
	}
	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public void setId(int id) {
		this.id = id;
	}
	
//	public String stringify() {
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			return mapper.writeValueAsString(this);
//		} catch (JsonProcessingException e) {
//			return "OOOOOOOOOOHHH!!!";
//		}
//	}
	
	
	
}
