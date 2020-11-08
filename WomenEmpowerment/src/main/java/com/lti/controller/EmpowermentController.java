package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.CourseOperationDto;
import com.lti.dto.EnrollmentStatusDto;
import com.lti.dto.ForgotStatusDto;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatusDto;
import com.lti.dto.NGOStatusDto;
import com.lti.dto.NgoOperationStatusDto;
import com.lti.dto.StatusDto;
import com.lti.dto.StatusDto.StatusType;
import com.lti.entity.Course;
import com.lti.entity.NGO;
import com.lti.entity.User;
import com.lti.exception.WomenEmpowermentException;
import com.lti.service.ForgotPasswordService;
import com.lti.service.WomenEmpowermentService;

@RestController
@CrossOrigin
public class EmpowermentController {

	@Autowired
	WomenEmpowermentService services;
	
	@Autowired
	ForgotPasswordService forgotservice;
	
	boolean userOTPSent,ngoOTPSent,adminOTPSent;
	boolean userVerified,ngoVerified,adminVerified;
	
	@PostMapping("/forgotUser")
	public StatusDto userForgot(@RequestBody LoginDto loginDto) {
		services.forgotUserPassword(loginDto.getEmail());
		StatusDto otpstatus=new StatusDto();
		otpstatus.setMessage("OTP Sent");
		otpstatus.setStatus(StatusType.SUCCESS);
		userOTPSent=true;
		return otpstatus;
	}
	@PostMapping("/userOTPverify")
	public StatusDto userVerify(@RequestBody LoginDto loginDto) {
		boolean a=services.verifyUserOTP(loginDto.getOtp());
		StatusDto otpvstatus=new StatusDto();
		if(userOTPSent==true) {
			if(a==true) {
				otpvstatus.setMessage("OTP Verified!");
				otpvstatus.setStatus(StatusType.SUCCESS);
				userVerified=true;
				return otpvstatus;
			}
		}
		otpvstatus.setMessage("OTP Verification Failed!");
		otpvstatus.setStatus(StatusType.FAILURE);
		userVerified=false;
		return otpvstatus;
	}
	@PostMapping("/userChangePassword")
	public StatusDto userChangePassword(@RequestBody LoginDto passwordDto) {
		try {
			if(userVerified==true) {
				forgotservice.setUserPassword(passwordDto.getEmail(), passwordDto.getPassword());
				StatusDto status = new StatusDto();
	            status.setStatus(StatusType.SUCCESS);
	            status.setMessage("Password change successful!");
	            return status;
			}
			StatusDto status = new StatusDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage("Not verified!");
            return status;
		}
		catch(WomenEmpowermentException e) {
			StatusDto status = new StatusDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage("Not verified!");
            return status;
		}
		
	}
	
	@PostMapping("/forgotNgo")
	public StatusDto ngoForgot(@RequestBody LoginDto loginDto) {
		services.forgotUserPassword(loginDto.getEmail());
		StatusDto otpstatus=new StatusDto();
		otpstatus.setMessage("OTP Sent");
		otpstatus.setStatus(StatusType.SUCCESS);
		ngoOTPSent=true;
		return otpstatus;
	}
	@PostMapping("/ngoOTPverify")
	public StatusDto ngoVerify(@RequestBody LoginDto loginDto) {
		boolean a=services.verifyNGOOTP(loginDto.getOtp());
		StatusDto otpvstatus=new StatusDto();
		if(ngoOTPSent==true) {
			if(a==true) {
				otpvstatus.setMessage("OTP Verified!");
				otpvstatus.setStatus(StatusType.SUCCESS);
				ngoVerified=true;
				return otpvstatus;
			}
		}
		otpvstatus.setMessage("OTP Verification Failed!");
		otpvstatus.setStatus(StatusType.FAILURE);
		ngoVerified=false;
		return otpvstatus;
	}
	@PostMapping("/ngoChangePassword")
	public StatusDto ngoChangePassword(@RequestBody LoginDto passwordDto) {
		try {
			if(ngoVerified==true) {
				forgotservice.setNGOPassword(passwordDto.getEmail(), passwordDto.getPassword());
				StatusDto status = new StatusDto();
	            status.setStatus(StatusType.SUCCESS);
	            status.setMessage("Password change successful!");
	            return status;
			}
			StatusDto status = new StatusDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage("Not verified!");
            return status;
		}
		catch(WomenEmpowermentException e) {
			StatusDto status = new StatusDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage("Not verified!");
            return status;
		}
	}
	
	@PostMapping("/forgotAdmin")
	public StatusDto adminForgot(@RequestBody LoginDto loginDto) {
		services.forgotAdminPassword(loginDto.getEmail());
		StatusDto otpstatus=new StatusDto();
		otpstatus.setMessage("OTP Sent");
		otpstatus.setStatus(StatusType.SUCCESS);
		adminOTPSent=true;
		return otpstatus;
	}
	@PostMapping("/adminOTPverify")
	public StatusDto adminVerify(@RequestBody LoginDto loginDto) {
		boolean a=services.verifyAdminOTP(loginDto.getOtp());
		StatusDto otpvstatus=new StatusDto();
		if(adminOTPSent==true) {
			if(a==true) {
				otpvstatus.setMessage("OTP Verified!");
				otpvstatus.setStatus(StatusType.SUCCESS);
				adminVerified=true;
				return otpvstatus;
			}
		}
		otpvstatus.setMessage("OTP Verification Failed!");
		otpvstatus.setStatus(StatusType.FAILURE);
		adminVerified=false;
		return otpvstatus;
	}
	@PostMapping("/adminChangePassword")
	public StatusDto adminChangePassword(@RequestBody LoginDto passwordDto) {
		try {
			if(adminVerified==true) {
				forgotservice.setAdminPassword(passwordDto.getEmail(), passwordDto.getPassword());
				StatusDto status = new StatusDto();
	            status.setStatus(StatusType.SUCCESS);
	            status.setMessage("Password change successful!");
	            return status;
			}
			StatusDto status = new StatusDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage("Not verified!");
            return status;
		}
		catch(WomenEmpowermentException e) {
			StatusDto status = new StatusDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage("Not verified!");
            return status;
		}
	}
	
	@PostMapping(path = "/registerUser")
	public StatusDto registerUser(@RequestBody User user) {
	    try {
	            services.userRegister(user);
	            
	            StatusDto status = new StatusDto();
	            status.setStatus(StatusType.SUCCESS);
	            status.setMessage("Registration successful!");
	            return status;
        }
	    catch(WomenEmpowermentException e) {
	            StatusDto status = new StatusDto();
	            status.setStatus(StatusType.FAILURE);
	            status.setMessage(e.getMessage());
	            return status;
        }
    }
	@PostMapping(path = "/registerNgo")
	public StatusDto registerNgo(@RequestBody NGO ngo) {
	    try {
	            services.ngoRegister(ngo);
	            
	            StatusDto status = new StatusDto();
	            status.setStatus(StatusType.SUCCESS);
	            status.setMessage("Registration application successful!");
	            return status;
        }
	    catch(WomenEmpowermentException e) {
	            StatusDto status = new StatusDto();
	            status.setStatus(StatusType.FAILURE);
	            status.setMessage(e.getMessage());
	            return status;
        }
    }
	@PostMapping("/loginUser")
	public LoginStatusDto loginUser(@RequestBody LoginDto loginDto) {
		try {
			User user = services.userLogin(loginDto.getEmail(),loginDto.getPassword());
			LoginStatusDto loginStatus = new LoginStatusDto();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login Successful!");
			loginStatus.setId(user.getUserId());
			loginStatus.setName(user.getUserName());
			return loginStatus;
		}
		catch(WomenEmpowermentException e) {
			LoginStatusDto loginStatus = new LoginStatusDto();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;
		}
	}
	@PostMapping("/loginNGO")
	public LoginStatusDto loginNGO(@RequestBody LoginDto loginDto) {
		try {
			NGO ngo = services.ngoLogin(loginDto.getEmail(),loginDto.getPassword());
			LoginStatusDto loginStatus = new LoginStatusDto();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login Successful!");
			loginStatus.setId(ngo.getNgoId());
			loginStatus.setName(ngo.getNgoName());
			return loginStatus;
		}
		catch(WomenEmpowermentException e) {
			LoginStatusDto loginStatus = new LoginStatusDto();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;
		}
	}
	@PostMapping("/loginAdmin")
	public LoginStatusDto loginAdmin(@RequestBody LoginDto loginDto) {
	
		int x = services.adminLogin(loginDto.getEmail(),loginDto.getPassword());
		if(x==1) {
			LoginStatusDto loginStatus = new LoginStatusDto();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login Successful!");
			loginStatus.setId(10101);
			loginStatus.setName("Administrator");
			return loginStatus;
		}
		else{
			LoginStatusDto loginStatus = new LoginStatusDto();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage("Login failed!");
			return loginStatus;
		}
	}
	@PostMapping("/approveNGO")
	public NGOStatusDto approvalNGO(@RequestBody NGOStatusDto ngodto) {
		NGO ngo=services.findanNGOByEmail(ngodto.getNgoemail());
		 try {
	            services.approveNgo(ngo);
	            
	            NGOStatusDto status = new NGOStatusDto();
	            status.setStatus(StatusType.SUCCESS);
	            status.setMessage("Registration approval successful!");
	            status.setNgoemail(ngodto.getNgoemail());
	            status.setNgoName(ngodto.getNgoName());
	            return status;
		 }
	    catch(WomenEmpowermentException e) {
	            NGOStatusDto status = new NGOStatusDto();
	            status.setStatus(StatusType.FAILURE);
	            status.setMessage(e.getMessage());
	            return status;
	    }
	}
	@RequestMapping("/addCourse")
	public NgoOperationStatusDto addCourse(@RequestBody CourseOperationDto coursedto){
		Course c=new Course();
		NGO ngo=services.findanNGOByEmail(coursedto.getNgoEmail());
		c.setCourseName(coursedto.getCourseName());
		c.setCourseBenefits(coursedto.getCourseBenefits());
		c.setCourseEndDate(coursedto.getCourseEndDate());
		c.setCourseStartDate(coursedto.getCourseStartDate());
		c.setTrainingSector(coursedto.getTrainingSector());
		c.setCourseProvidingNGO(ngo.getNgoName());
		c.setNgo(ngo);
		try {
			services.addCourse(c, ngo);
			NgoOperationStatusDto status=new NgoOperationStatusDto();
			status.setMessage("Course addition successful!");
			status.setStatus(StatusType.SUCCESS);
			status.setNgoName(ngo.getNgoName());
			return status;
		}
		catch(WomenEmpowermentException e) {
			NgoOperationStatusDto status=new NgoOperationStatusDto();
			status.setMessage("Course addition unsuccessful!");
			status.setStatus(StatusType.FAILURE);
			status.setNgoName(ngo.getNgoName());
            return status;
		}
	}
	@RequestMapping("/editCourse")
	public NgoOperationStatusDto editCourse(@RequestBody CourseOperationDto coursedto){
		Course c=services.findCourseByCourseId(coursedto.getCourseId());
		NGO ngo=services.findanNGOByEmail(coursedto.getNgoEmail());
		c.setCourseName(coursedto.getCourseName());
		c.setCourseBenefits(coursedto.getCourseBenefits());
		c.setCourseEndDate(coursedto.getCourseEndDate());
		c.setCourseStartDate(coursedto.getCourseStartDate());
		c.setTrainingSector(coursedto.getTrainingSector());
		c.setCourseProvidingNGO(ngo.getNgoName());
		c.setNgo(ngo);
		try {
			services.editCourse(c, ngo);
			NgoOperationStatusDto status=new NgoOperationStatusDto();
			status.setMessage("Course updation successful!");
			status.setStatus(StatusType.SUCCESS);
			status.setNgoName(ngo.getNgoName());
			return status;
		}
		catch(WomenEmpowermentException e) {
			NgoOperationStatusDto status=new NgoOperationStatusDto();
			status.setMessage("Course updation unsuccessful!");
			status.setStatus(StatusType.FAILURE);
			status.setNgoName(ngo.getNgoName());
            return status;
		}
	}
	@RequestMapping("/deleteCourse")
	public NgoOperationStatusDto deleteCourse(@RequestBody CourseOperationDto coursedto){
		Course c=services.findCourseByCourseId(coursedto.getCourseId());
		NGO ngo=services.findanNGOByEmail(coursedto.getNgoEmail());
		try {
			services.deleteCourse(c, ngo);
			NgoOperationStatusDto status=new NgoOperationStatusDto();
			status.setMessage("Course deletion successful!");
			status.setStatus(StatusType.SUCCESS);
			status.setNgoName(ngo.getNgoName());
			return status;
		}
		catch(WomenEmpowermentException e) {
			NgoOperationStatusDto status=new NgoOperationStatusDto();
			status.setMessage("Course deletion unsuccessful!");
			status.setStatus(StatusType.FAILURE);
			status.setNgoName(ngo.getNgoName());
            return status;
		}
	}
	@RequestMapping("/viewAllUsers")
	public List<User> viewAllUsers(){
		try {
			List<User> users=services.viewAllUsers();
			return users;
		}
		catch(WomenEmpowermentException e) {
            return null;
		}
	}
	@RequestMapping("/viewAllNGOs")
	public List<NGO> viewAllNGOs(){
		try {
			List<NGO> ngos=services.viewAllNGOs();
			return ngos;
		}
		catch(WomenEmpowermentException e) {
            return null;
		}
	}
	@RequestMapping("/viewAllCourses")
	public List<Course> viewAllCourses(){
		try {
			List<Course> courses=services.viewAllCourses();
			return courses;
		}
		catch(WomenEmpowermentException e) {
            return null;
		}
	}
	@RequestMapping("/viewCoursesByNGO")
	public List<Course> viewCoursesByNGO(@RequestBody NGOStatusDto ngodto){
		int id=ngodto.getNgoId();
		System.out.println(id);
		try {
			List<Course> courses=services.viewCourseByNGO(id);
			return courses;
		}
		catch(WomenEmpowermentException e) {
            return null;
		}
	}
	@RequestMapping("/viewCoursesBySector")
	public List<Course> viewCoursesBySector(@RequestBody CourseOperationDto coursedto){
		String sector=coursedto.getTrainingSector();
		try {
			List<Course> courses=services.viewCourseBySector(sector);
			return courses;
		}
		catch(WomenEmpowermentException e) {
            return null;
		}
	}
	@RequestMapping("/registerWithAnNgo")
	public EnrollmentStatusDto registerWithAnNGO(@RequestBody EnrollmentStatusDto enrolldto) {
		User user=services.findUserById(enrolldto.getUserId());
		NGO ngo=services.findNGOById(enrolldto.getNgoId());
		try {
				services.RegisterWithNgo(user, ngo);
				EnrollmentStatusDto status=new EnrollmentStatusDto();
				status.setMessage("You are registered with "+ngo.getNgoName());
				status.setStatus(StatusType.SUCCESS);
				status.setNgoName(ngo.getNgoName());
				return status;
		}
		catch(WomenEmpowermentException e) {
			EnrollmentStatusDto status=new EnrollmentStatusDto();
			status.setMessage("User NGO registration unsuccessful!User registered with an NGO");
			status.setStatus(StatusType.FAILURE);
            return status;
		}
		
	}
}
