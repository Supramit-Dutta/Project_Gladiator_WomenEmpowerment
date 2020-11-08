package com.lti.repository;

import java.util.List;
import com.lti.entity.Accomodation;
import com.lti.entity.Course;
import com.lti.entity.Enroll;
import com.lti.entity.NGO;
import com.lti.entity.SukanyaYojna;
import com.lti.entity.User;

public interface EmpowermentRepository {

	public int registerAUser(User user);
	public List<Course> viewAllCourses();
	public Enroll applyEnrollmentForCourse(Enroll enroll);
	public int approveEnrollment(Enroll enroll);
	public void applyAccomodation(Accomodation accomodation);
	public int approveAccomodation(Accomodation acc);
	public void applySukanyaScheme(SukanyaYojna sukanya);
	public int approveSukanyaScheme(SukanyaYojna sukanya);
	public int registerAnNGO(NGO ngo);
	public User logInUser(String userEmail,String userPassword);
	public int addACourse(Course course);
	public int editACourse(Course course);
	public int deleteACourse(int courseId);
	public Course findACourse(int courseId);
	public List<Enroll> viewAllEnrollment();
	public List<User> viewAllUsers();
	public User findAUser(int userId);
	public NGO findAnNGO(int ngoId);
	public Enroll findAnEnrollment(int enrollmentId);
	public List<Enroll> findNotApprovedEnroll();
	public List<SukanyaYojna> viewNotApprovedSukanya();
	public List<Accomodation> viewNotApprovedAccomodation();
	public Accomodation findAccomodation(int accomodationId);
	public SukanyaYojna findASukanya(int schemeId);
	public NGO logInNGO(String ngoEmail,String ngoPassword);
	public int logInAdmin(String adminEmail,String adminPassword);
	public List<Course> viewCourseBySector(String trainingSector);
	public List<Course> viewCourseByNGO(int ngoId);
	public List<NGO> viewAllNGO();
	public int approveNGO(NGO ngo);
	public int updateUserPassword(String userEmail,String newPassword);
	public int updateNGOPassword(String ngoEmail,String newPassword);
	public int updateAdminPassword(String email,String newPassword);
	public boolean isUserPresent(String email);
	public boolean isNGOPresent(String email);
	public NGO findNGOByEmail(String email);
	public boolean isCoursePresent(String courseName);
	public boolean isAlreadyRegisteredWithNgo(int ngoId);
	public int deleteANonRegisteredUser(int userId);
	public boolean isAlreadyEnrolled(int userId,int courseId);
}
