package com.police_resource_manager.prms.leave;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.police_resource_manager.prms.officers.Officer;


@Entity
@Table(name="officer_leave")
//@NamedQuery(name="Leave.findAll", query="SELECT l FROM Leave l")
public class Leave implements Serializable{
	
	@Override
	public String toString() {
		return "Leave [id=" + id + ", officer=" + officer + ", endDate=" + endDate + ", address=" + address + "]";
	}

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
//	@JsonUnwrapped
	private LeaveId id;
	
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@ManyToOne
	@MapsId("officerRegNo")
	@JoinColumn(name="officer_reg_no")
//	@JsonManagedReference
	private Officer officer;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	private String address;

//	@JsonBackReference
	public LeaveId getId() {
		return id;
	}

	public void setId(LeaveId id) {
		this.id = id;
	}
	
//	@JsonBackReference
	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}