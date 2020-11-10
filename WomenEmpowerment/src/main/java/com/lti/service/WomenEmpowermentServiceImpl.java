package com.lti.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.config.CustomRepositoryImplementationDetector;
import org.springframework.stereotype.Service;

import com.lti.entity.Accomodation;
import com.lti.entity.Course;
import com.lti.entity.Enroll;
import com.lti.entity.HomeList;
import com.lti.entity.NGO;
import com.lti.entity.SukanyaYojna;
import com.lti.entity.User;
import com.lti.exception.WomenEmpowermentException;
import com.lti.repository.EmpowermentRepository;

@Service
public class WomenEmpowermentServiceImpl implements WomenEmpowermentService {

	@Autowired
	EmpowermentRepository repo;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ForgotPasswordService passwordService;
	
	String userOTP,ngoOTP,adminOTP;
	
	public int adminLogin(String email,String password) {
		int c=repo.logInAdmin(email, password);
		if(c==0)
			throw new WomenEmpowermentException("Incorrect email/password!");
		return c;
	}
	public User userLogin(String email, String password) {
		try {
            if(!repo.isUserPresent(email))
                throw new WomenEmpowermentException("User is not registered!!");
            User user = repo.logInUser(email, password);
            return user;
        }
        catch(EmptyResultDataAccessException e) {
            throw new WomenEmpowermentException("Incorrect email/password!");
        }
	}

	public NGO ngoLogin(String email, String password) {
		try {
            if(!repo.isNGOPresent(email))
                throw new WomenEmpowermentException("NGO is not registered!!");
            NGO ngo = repo.logInNGO(email, password);
            return ngo;
        }
        catch(EmptyResultDataAccessException e) {
            throw new WomenEmpowermentException("Incorrect email/password!");
        }
	}

	public void forgotUserPassword(String email) {
		Random rnd=new Random();
		int number=rnd.nextInt(999999);
		userOTP=String.format("%06d",number);
		String text="This is your OTP for new Password Generation. Your OTP is "+userOTP;
        String subject="Verification For Password Reset";
        emailService.sendEmailForOTP(email, text, subject);
	}

	public void forgotNGOPassword(String email) {
		Random rnd=new Random();
		int number=rnd.nextInt(999999);
		ngoOTP=String.format("%06d",number);
		String text="This is your OTP for new Password Generation. Your OTP is "+ngoOTP;
        String subject="Verification For Password Reset";
        emailService.sendEmailForOTP(email, text, subject);
	}

	public void forgotAdminPassword(String email) {
		Random rnd=new Random();
		int number=rnd.nextInt(999999);
		adminOTP=String.format("%06d",number);
		String text="This is your OTP for new Password Generation. Your OTP is "+adminOTP;
        String subject="Verification For Password Reset";
        emailService.sendEmailForOTP(email, text, subject);
	}

	public boolean verifyUserOTP(String UserOTP) {
        if(UserOTP.equals(userOTP)) {
        	userOTP="";
        	return true;
        }    
        else {
        	return false;
        }	
	}

	public boolean verifyNGOOTP(String NgoOTP) {
		if(NgoOTP.equals(ngoOTP)) {
        	ngoOTP="";
        	return true;
        }    
        else {
        	return false;
        }	
	}

	public boolean verifyAdminOTP(String AdminOTP) {
		if(AdminOTP.equals(adminOTP)) {
        	adminOTP="";
        	return true;
        }    
        else {
        	return false;
        }	
	}

	public void userRegister(User user) {
		if(!repo.isUserPresent(user.getUserEmail())) {
            int id=repo.registerAUser(user);
            String text="You are successfully registered. Your registration id is "+id;
            String subject="Registration Confirmation";
            emailService.sendEmailForNewRegistration(user.getUserEmail(), text, subject);
        }
        else
            throw new WomenEmpowermentException("User already registered!");
	}

	public void ngoRegister(NGO ngo) {
		if(!repo.isNGOPresent(ngo.getNgoEmail())) {
            int id=repo.registerAnNGO(ngo);
            String text="NGO is applied for registration. You will get a confirmtion once registration is approved";
            String subject="Registration Pending";
            emailService.sendEmailForNewRegistration(ngo.getNgoEmail(), text, subject);
        }
        else
            throw new WomenEmpowermentException("NGO already registered!");
	}
	public void approveNgo(NGO ngo) {
		if(ngo.getNgoApplicationStatus().equals("Pending")) {
			int check=repo.approveNGO(ngo);
			if(check==1) {
				String text="NGO is approved. Your registration id is:"+ngo.getNgoId();
				String subject="Registration Confirmation";
				emailService.sendEmailForNewRegistration(ngo.getNgoEmail(), text, subject);
			}
		}
		else
			throw new WomenEmpowermentException("Application already approved!");
	}
	public NGO findanNGOByEmail(String ngoEmail) {
		NGO ngo=repo.findNGOByEmail(ngoEmail);
		return ngo;
	}
	public void addCourse(Course course, NGO ngo) {
		if(!repo.isCoursePresent(course.getCourseName())) {
            int id=repo.addACourse(course);
            String text="Course is successfully added. Your course id is "+id;
            String subject="Course Addition Confirmation";
            emailService.sendEmailForNewRegistration(ngo.getNgoEmail(), text, subject);
        }
        else
            throw new WomenEmpowermentException("Course already added!");
	}
	public void editCourse(Course course,NGO ngo) {
		if(repo.isCoursePresent(course.getCourseName())) {
            int id=repo.editACourse(course);
            String text="Course is successfully edited. Your edited course id is "+id;
            String subject="Course Updation Confirmation";
            emailService.sendEmailForNewRegistration(ngo.getNgoEmail(), text, subject);
        }
        else
            throw new WomenEmpowermentException("Course does not exist!");
	}
	public void deleteCourse(Course course,NGO ngo) {
		if(repo.isCoursePresent(course.getCourseName())) {
            int id=repo.deleteACourse(course.getCourseId());
            if(id==1) {
            	String text="Course is successfully deleted. Your deleted course id was "+course.getCourseId();
                String subject="Course Deletion Confirmation";
                emailService.sendEmailForNewRegistration(ngo.getNgoEmail(), text, subject);
            }
        }
        else
            throw new WomenEmpowermentException("Course does not exist!");
	}
	public Course findCourseByCourseId(int courseId) {
		Course course=repo.findACourse(courseId);
		return course;
	}
	public List<User> viewAllUsers(){
		List<User> users=repo.viewAllUsers();
		return users;
	}
	public List<NGO> viewAllNGOs(){
		List<NGO> ngos=repo.viewAllNGO();
		return ngos;
	}
	public List<Course> viewAllCourses(){
		List<Course> courses=repo.viewAllCourses();
		return courses;
	}
	public List<Course> viewCourseBySector(String trainingSector){
		List<Course> courses=repo.viewCourseBySector(trainingSector);
		return courses;
	} 
	public List<Course> viewCourseByNGO(int ngoId){
		List<Course> courses=repo.viewCourseByNGO(ngoId);
		return courses;
	} 
	public User findUserById(int userId) {
		User user=repo.findAUser(userId);
		return user;
	}
	public NGO findNGOById(int ngoId) {
		NGO ngo=repo.findAnNGO(ngoId);
		return ngo;
	}
	public void RegisterWithNgo(User user,NGO ngo) {
		int oldId=user.getUserId();
		if(repo.isAlreadyRegisteredWithNgo(user.getUserId())) {
			user.setNgo(ngo);
			int newId=repo.registerAUser(user);
			System.out.println(user.getNgo().getNgoName());
			String text="You have been registered with an NGO. Your NGO name is "+ngo.getNgoName();
            String subject="NGO-User Registration Confirmation";
            emailService.sendEmailForNewRegistration(user.getUserEmail(), text, subject);
		}
		else
			throw new WomenEmpowermentException("NGO-User registration unsuccessful");
	}
	public Course findCourseById(int courseId) {
		Course c=repo.findACourse(courseId);
		return c;
	}
	public void applyEnroll(Enroll enroll,int userId,int courseId) {
		if(!repo.isAlreadyEnrolled(userId, courseId)) {
			Enroll e=repo.applyEnrollmentForCourse(enroll);
			User user=repo.findAUser(userId);
			if(e!=null) {
				String text="Your application for enrollment is received. You will get an approval soon.";
	            String subject="Enrollment Application Confirmation";
	            emailService.sendEmailForNewRegistration(user.getUserEmail(), text, subject);
			}
		}
		else
			throw new WomenEmpowermentException("You are already enrolled in the course!");
		
	}
	public List<Enroll> getUnApprovedEnrolls(){
		List<Enroll> unapp=repo.findNotApprovedEnroll();
		return unapp;
	}
	public int approveEnroll(Enroll e) {
		int x=repo.approveEnrollment(e);
		return x;
	}
	public List<Enroll> viewAllEnrolls(){
		List<Enroll> enrolls=repo.viewAllEnrollment();
		return enrolls;
	}
	public List<NGO> viewAllApprovedNGO() {
		List<NGO> ngos=repo.viewAllApprovedNGO();
		return ngos;
	}
	public void applyAccomodation(Accomodation acc,int userId) {
		if(!repo.isAlreadyAccomodated(userId)) {
			Accomodation a=repo.applyAccomodation(acc);
			User user=repo.findAUser(userId);
			if(a!=null) {
				String text="Your application for accomodation is received. You will get an approval soon.";
	            String subject="Accomodation Application Confirmation";
	            emailService.sendEmailForNewRegistration(user.getUserEmail(), text, subject);
			}
		}
		else
			throw new WomenEmpowermentException("You have already been provided accomodation!");
	}
	
	public int addHome(HomeList home) {
		if(!repo.isHomeAlreadyPresent(home.getCity())) {
			int id=repo.addHome(home);
			return id;
		}
		else {
			throw new WomenEmpowermentException("City is already present!");
		}
	}

	public int updateHome(HomeList home, int rooms, int delete) {
		int x=repo.updateHome(home, rooms, delete);
		if(x!=0)
			return x;
		else
			throw new WomenEmpowermentException("Couldn't update");
	}
	
	public HomeList findHomeByCityId(int cityId) {
		HomeList h=repo.findHomeByCityId(cityId);
		return h;
	}
	public List<HomeList> viewAllHomes(){
		List<HomeList> homes=repo.viewAllHomes();
		return homes;
	}
	public void applySukanya(SukanyaYojna sy,int userId) {
		if(!repo.isAlreadyASukanya(userId)) {
			SukanyaYojna s=repo.applySukanyaScheme(sy);
			User user=repo.findAUser(userId);
			if(s!=null) {
				String text="Your application for Sukanya Yojna is received. You will get an approval soon.";
	            String subject="Sukanya Yojna Application Confirmation";
	            emailService.sendEmailForNewRegistration(user.getUserEmail(), text, subject);
			}
		}
		else
			throw new WomenEmpowermentException("You are already a part of Sukanya Yojna Scheme!");
	}
	public List<Accomodation> viewAccomodations(){
		List<Accomodation> accs=repo.viewAccomodation();
		return accs;
	}
	public List<SukanyaYojna> viewSukanyas(){
		List<SukanyaYojna> suks=repo.viewSukanya();
		return suks;
	}
	public List<SukanyaYojna> getUnApprovedSukanyas(){
		List<SukanyaYojna> unapprovedsukanyas=repo.viewNotApprovedSukanya();
		return unapprovedsukanyas;
	}
	public int approveSukanya(SukanyaYojna sy) {
		int x=repo.approveSukanyaScheme(sy);
		return x;
	}
	
	public List<Accomodation> viewNotApprovedWorkingPhysical() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingPhysical();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingST() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingST();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingSC() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingSC();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingOBC() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingOBC();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingEWS() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingEWS();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingHusband() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingHusband();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingMetropolitan() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingMetropolitan();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedWorkingNonMetropolitan() {
		List<Accomodation> acc=repo.viewNotApprovedWorkingNonMetropolitan();
		return acc;
	}
	
	public List<Accomodation> viewNotApprovedNotWorking() {
		List<Accomodation> acc=repo.viewNotApprovedNotWorking();
		return acc;
	}
	
	public int approveAccomodation(Accomodation acc) {
		int x=repo.approveAccomodation(acc);
		return x;
	}
	
	public int rejectAccomodation(Accomodation acc) {
		int x=repo.rejectAccomodation(acc);
		return x;
	}
	
}
