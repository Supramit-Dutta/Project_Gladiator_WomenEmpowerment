package com.lti.dto;

import java.time.LocalDate;

public class CourseOperationDto {

	int courseId;
	String courseName;
	String courseStartDate;
	String courseEndDate;
	String courseProvidingNGO;
	String courseBenefits;
	String trainingSector;
	String ngoEmail;
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseStartDate() {
		return courseStartDate;
	}
	public void setCourseStartDate(String courseStartDate) {
		this.courseStartDate = courseStartDate;
	}
	public String getCourseEndDate() {
		return courseEndDate;
	}
	public void setCourseEndDate(String courseEndDate) {
		this.courseEndDate = courseEndDate;
	}
	public String getCourseProvidingNGO() {
		return courseProvidingNGO;
	}
	public void setCourseProvidingNGO(String courseProvidingNGO) {
		this.courseProvidingNGO = courseProvidingNGO;
	}
	public String getCourseBenefits() {
		return courseBenefits;
	}
	public void setCourseBenefits(String courseBenefits) {
		this.courseBenefits = courseBenefits;
	}
	public String getTrainingSector() {
		return trainingSector;
	}
	public void setTrainingSector(String trainingSector) {
		this.trainingSector = trainingSector;
	}
	public String getNgoEmail() {
		return ngoEmail;
	}
	public void setNgoEmail(String ngoEmail) {
		this.ngoEmail = ngoEmail;
	}
}
