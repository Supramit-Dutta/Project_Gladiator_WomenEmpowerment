package com.lti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.ForgotStatusDto;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatusDto;
import com.lti.dto.StatusDto;
import com.lti.dto.StatusDto.StatusType;
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
}
