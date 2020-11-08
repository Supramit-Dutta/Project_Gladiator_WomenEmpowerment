package com.lti.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "table_enroll_we1")
public class Enroll {
	
	@Id
	@SequenceGenerator(name = "enroll_seq", initialValue = 4001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enroll_seq")
	int enrollmentId;
	
	String userEmploymentStatus;
	LocalDate enrollmentDate ;
	String userEnrollmentStatus ;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	User user;
	
	@OneToOne
	@JoinColumn(name="course_id")
	@JsonIgnore
	Course course;
	
	@JsonIgnore
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	public String getUserEnrollmentStatus() {
		return userEnrollmentStatus;
	}
	public void setUserEnrollmentStatus(String userEnrollmentStatus) {
		this.userEnrollmentStatus = userEnrollmentStatus;
	}
	public String getUserEmploymentStatus() {
		return userEmploymentStatus;
	}
	public void setUserEmploymentStatus(String userEmploymentStatus) {
		this.userEmploymentStatus = userEmploymentStatus;
	}
}
