package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="table_sukanya_we1")
public class SukanyaYojna {
	
	@Id
	@SequenceGenerator(name = "yojna_seq", initialValue = 5001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yojna_seq")
	int schemeId;
	
	int girlChildAge;
	String girlChildNationality;
	double initialDepositAmount;
	String applicationStatus;
	
	@OneToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	User user;
	
	
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getGirlChildAge() {
		return girlChildAge;
	}
	public void setGirlChildAge(int girlChildAge) {
		this.girlChildAge = girlChildAge;
	}
	public String getGirlChildNationality() {
		return girlChildNationality;
	}
	public void setGirlChildNationality(String girlChildNationality) {
		this.girlChildNationality = girlChildNationality;
	}
	public double getInitialDepositAmount() {
		return initialDepositAmount;
	}
	public void setInitialDepositAmount(double initialDepositAmount) {
		this.initialDepositAmount = initialDepositAmount;
	}
}
