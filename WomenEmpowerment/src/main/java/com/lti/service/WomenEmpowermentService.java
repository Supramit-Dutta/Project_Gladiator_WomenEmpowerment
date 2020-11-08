package com.lti.service;

import java.util.List;

import com.lti.entity.Course;
import com.lti.entity.NGO;
import com.lti.entity.User;

public interface WomenEmpowermentService {

	User userLogin(String email,String password);
	NGO  ngoLogin(String email,String password);
	void forgotUserPassword(String email);
	void forgotNGOPassword(String email);
	void forgotAdminPassword(String email);
	boolean verifyUserOTP(String UserOTP);
	boolean verifyNGOOTP(String NgoOTP);
	boolean verifyAdminOTP(String AdminOTP);
	void userRegister(User user);
	void ngoRegister(NGO ngo);
	int adminLogin(String email,String password);
	public void approveNgo(NGO ngo);
	public NGO findanNGOByEmail(String ngoEmail);
	public void addCourse(Course course, NGO ngo);
	public void editCourse(Course course, NGO ngo);
	public void deleteCourse(Course course, NGO ngo);
	public Course findCourseByCourseId(int courseId);
	public List<User> viewAllUsers();
	public List<NGO> viewAllNGOs();
	public List<Course> viewAllCourses();
	public List<Course> viewCourseBySector(String trainingSector);
	public List<Course> viewCourseByNGO(int ngoId);
	public User findUserById(int userId);
	public NGO findNGOById(int userId);
	public void RegisterWithNgo(User user,NGO ngo);
	public Course findCourseById(int courseId);
}
