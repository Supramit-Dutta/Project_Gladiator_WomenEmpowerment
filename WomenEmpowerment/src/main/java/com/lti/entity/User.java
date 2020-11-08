package com.lti.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "table_user_we1")
public class User {
	
	@Id
	@SequenceGenerator(name = "user_seq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	int userId;

	String userPassword;
	String userName;
	String userGender;
	LocalDate userDateOfBirth;
	String userEmail;
	String userAddress;
	String userMaritalStatus;
	String userNationality;
	
	@OneToOne
	@JoinColumn(name="ngo_id")
	@JsonIgnore
	NGO ngo;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	@JsonIgnore
	List<Enroll> enrolls;
	
	@JsonIgnore
	public NGO getNgo() {
		return ngo;
	}

	public void setNgo(NGO ngo) {
		this.ngo = ngo;
	}

	@JsonIgnore
	public List<Enroll> getEnrolls() {
		return enrolls;
	}

	public void setEnrolls(List<Enroll> enrolls) {
		this.enrolls = enrolls;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public LocalDate getUserDateOfBirth() {
		return userDateOfBirth;
	}

	public void setUserDateOfBirth(LocalDate userDateOfBirth) {
		this.userDateOfBirth = userDateOfBirth;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserMaritalStatus() {
		return userMaritalStatus;
	}

	public void setUserMaritalStatus(String userMaritalStatus) {
		this.userMaritalStatus = userMaritalStatus;
	}

	public String getUserNationality() {
		return userNationality;
	}

	public void setUserNationality(String userNationality) {
		this.userNationality = userNationality;
	}
}
