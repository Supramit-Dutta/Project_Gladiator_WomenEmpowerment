package com.lti.dto;

public class EnrollmentStatusDto extends StatusDto {
	int userId;
	int ngoId;
	String ngoName;
	String courseName;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNgoId() {
		return ngoId;
	}
	public void setNgoId(int ngoId) {
		this.ngoId = ngoId;
	}
	public String getNgoName() {
		return ngoName;
	}
	public void setNgoName(String ngoName) {
		this.ngoName = ngoName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
