package com.police_resource_manager.prms.officers;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.police_resource_manager.prms.formation.OfficerFormationAssign;
import com.police_resource_manager.prms.leave.Leave;
import com.police_resource_manager.prms.roles.Role;
import com.police_resource_manager.prms.schedule.Schedule;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="officer")
@NamedQuery(name="Officer.findAll", query="SELECT o FROM Officer o")
@JsonIgnoreProperties({ "leaves" })
public class Officer {

	
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Pattern(regexp = "^[A-Za-z]+$", message="First Name must only contain letters of the alphabet")
	@NotBlank(message="First Name cannot be blank")
	@Column(name="first_name")
	private String firstName;
	
	@Pattern(regexp = "^[A-Za-z]+$", message="Last Name must only contain letters of the alphabet")
	@NotBlank(message="Last Name cannot be blank")
	@Column(name="last_name")
	private String lastName;
	
	@Id
	@Column(name="reg_no")
	private int regNo;

	private String rank;
	
	
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	
	@Column(name="enlistment_date")
	private LocalDate enlistmentDate;
	
	private String telephone;
	
	@Column(name="jcf_email")
	private String JCFEmail;
	
	@Column(name="email_password")
	private String emailPassword;

	private String address;
	
	@OneToMany(mappedBy="officer")
	private List<Schedule> schedule;
	
	@JsonIgnoreProperties("officers")
	@OneToMany(mappedBy="officer")
	private List<OfficerFormationAssign> formationAssignments;
	
	@JsonManagedReference
	public List<OfficerFormationAssign> getFormationAssignments() {
		return formationAssignments;
	}

	public void setFormationAssignments(List<OfficerFormationAssign> formationAssignments) {
		this.formationAssignments = formationAssignments;
	}

	@ManyToMany
	@JoinTable(
		name="officer_role"
		, joinColumns={
			@JoinColumn(name="officer_reg_no")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private List<Role> roles;
	
//	@JsonManagedReference
	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}

	@OneToMany(mappedBy="officer")
	private List<Leave> leaves;

	public Officer() {}
	
	public Officer(String firstName, String lastName, int regNo, String rank, LocalDate dateOfBirth,
			LocalDate enlistmentDate, String telephone, String JCFEmail, String emailPassword, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.regNo = regNo;
		this.rank = rank;
		this.dateOfBirth = dateOfBirth;
		this.enlistmentDate = enlistmentDate;
		this.telephone = telephone;
		this.JCFEmail = JCFEmail;
		this.emailPassword = emailPassword;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRegNo() {
		return regNo;
	}

	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getEnlistmentDate() {
		return enlistmentDate;
	}

	public void setEnlistmentDate(LocalDate enlistmentDate) {
		this.enlistmentDate = enlistmentDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getJCFEmail() {
		return JCFEmail;
	}

	@JsonManagedReference
	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setJCFEmail(String jCFEmail) {
		JCFEmail = jCFEmail;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}