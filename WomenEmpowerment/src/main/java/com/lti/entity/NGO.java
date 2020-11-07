package com.lti.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_ngo")
public class NGO {

	@Id
	@SequenceGenerator(name = "ngo_seq", initialValue = 3001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ngo_seq")
	public int ngoId;
	
	String ngoApplicationStatus;
	String ngoName;
	int ngoRegistrationNumber;
	String ngoUserName;
	String ngoPassword;
	String ngoLocation;
	String ngoEmail;
	
	
	@OneToMany(mappedBy="ngo",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	List<Course> courses;
	
	
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public int getNgoId() {
		return ngoId;
	}
	public void setNgoId(int ngoId) {
		this.ngoId = ngoId;
	}
	public String getNgoApplicationStatus() {
		return ngoApplicationStatus;
	}
	public void setNgoApplicationStatus(String ngoApplicationStatus) {
		this.ngoApplicationStatus = ngoApplicationStatus;
	}
	public String getNgoName() {
		return ngoName;
	}
	public void setNgoName(String ngoName) {
		this.ngoName = ngoName;
	}
	public int getNgoRegistrationNumber() {
		return ngoRegistrationNumber;
	}
	public void setNgoRegistrationNumber(int ngoRegistrationNumber) {
		this.ngoRegistrationNumber = ngoRegistrationNumber;
	}
	public String getNgoUserName() {
		return ngoUserName;
	}
	public void setNgoUserName(String ngoUserName) {
		this.ngoUserName = ngoUserName;
	}
	public String getNgoPassword() {
		return ngoPassword;
	}
	public void setNgoPassword(String ngoPassword) {
		this.ngoPassword = ngoPassword;
	}
	public String getNgoLocation() {
		return ngoLocation;
	}
	public void setNgoLocation(String ngoLocation) {
		this.ngoLocation = ngoLocation;
	}
	public String getNgoEmail() {
		return ngoEmail;
	}
	public void setNgoEmail(String ngoEmail) {
		this.ngoEmail = ngoEmail;
	}
}
