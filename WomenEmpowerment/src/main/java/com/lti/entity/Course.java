package com.lti.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="table_course")
public class Course {
	
	@Id
	@SequenceGenerator(name = "course_seq", initialValue = 2001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
	int courseId;
	
	String courseName;
	LocalDate courseStartDate;
	LocalDate courseEndDate;
	String courseProvidingNGO;
	String courseBenefits;
	String trainingSector;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ngo_id")
	@JsonIgnore
	NGO ngo;
	
	@JsonIgnore
	public NGO getNgo() {
		return ngo;
	}
	public void setNgo(NGO ngo) {
		this.ngo = ngo;
	}
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
	public LocalDate getCourseStartDate() {
		return courseStartDate;
	}
	public void setCourseStartDate(LocalDate courseStartDate) {
		this.courseStartDate = courseStartDate;
	}
	public LocalDate getCourseEndDate() {
		return courseEndDate;
	}
	public void setCourseEndDate(LocalDate courseEndDate) {
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
}
