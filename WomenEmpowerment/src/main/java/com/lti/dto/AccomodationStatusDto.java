package com.lti.dto;

public class AccomodationStatusDto extends StatusDto {
	int userId;
	int accomodationId;
	String areaOfResidence;
	double grossIncomePerMonth;
	String caste;
	String husbandResidenceName;
	String userResidenceName;
	int numberOfGirlChildBelow18;
	int numberOfBoyChildBelow5;
	String anyPhysicalChallenges;
	String dayCareRequirements;
	String employmentStatus;
	String accomodationCity;
	
	public String getAccomodationCity() {
		return accomodationCity;
	}
	public void setAccomodationCity(String accomodationCity) {
		this.accomodationCity = accomodationCity;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAccomodationId() {
		return accomodationId;
	}
	public void setAccomodationId(int accomodationId) {
		this.accomodationId = accomodationId;
	}
	public String getAreaOfResidence() {
		return areaOfResidence;
	}
	public void setAreaOfResidence(String areaOfResidence) {
		this.areaOfResidence = areaOfResidence;
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
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	
}
