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
@Table(name="table_accomodation_we1")
public class Accomodation {
	
	@Id
	@SequenceGenerator(name = "acc_seq", initialValue = 101, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
	int accomodationId;
	
	double grossIncomePerMonth;
	String caste;
	String husbandResidenceName;
	String userResidenceName;
	int numberOfGirlChildBelow18;
	int numberOfBoyChildBelow5;
	String accomodationCity;
	String anyPhysicalChallenges;
	String dayCareRequirements;
	String applicationStatus;
	String employmentStatus;
	String areaOfResidence;
	
	@OneToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	User user;

	
	public String getAreaOfResidence() {
		return areaOfResidence;
	}

	public void setAreaOfResidence(String areaOfResidence) {
		this.areaOfResidence = areaOfResidence;
	}

	public String getAccomodationCity() {
		return accomodationCity;
	}

	public void setAccomodationCity(String accomodationCity) {
		this.accomodationCity = accomodationCity;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getHusbandResidenceName() {
		return husbandResidenceName;
	}

	public void setHusbandResidenceName(String husbandResidenceName) {
		this.husbandResidenceName = husbandResidenceName;
	}

	public String getUserResidenceName() {
		return userResidenceName;
	}

	public void setUserResidenceName(String userResidenceName) {
		this.userResidenceName = userResidenceName;
	}

	public int getNumberOfGirlChildBelow18() {
		return numberOfGirlChildBelow18;
	}

	public void setNumberOfGirlChildBelow18(int numberOfGirlChildBelow18) {
		this.numberOfGirlChildBelow18 = numberOfGirlChildBelow18;
	}

	public int getNumberOfBoyChildBelow5() {
		return numberOfBoyChildBelow5;
	}

	public void setNumberOfBoyChildBelow5(int numberOfBoyChildBelow5) {
		this.numberOfBoyChildBelow5 = numberOfBoyChildBelow5;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public int getAccomodationId() {
		return accomodationId;
	}

	public void setAccomodationId(int accomodationId) {
		this.accomodationId = accomodationId;
	}
	public double getGrossIncomePerMonth() {
		return grossIncomePerMonth;
	}

	public void setGrossIncomePerMonth(double grossIncomePerMonth) {
		this.grossIncomePerMonth = grossIncomePerMonth;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getAnyPhysicalChallenges() {
		return anyPhysicalChallenges;
	}

	public void setAnyPhysicalChallenges(String anyPhysicalChallenges) {
		this.anyPhysicalChallenges = anyPhysicalChallenges;
	}

	public String getDayCareRequirements() {
		return dayCareRequirements;
	}

	public void setDayCareRequirements(String dayCareRequirements) {
		this.dayCareRequirements = dayCareRequirements;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
