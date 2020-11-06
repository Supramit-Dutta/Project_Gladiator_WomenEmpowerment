package com.lti.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lti.entity.NGO;
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
            String text="NGO is successfully registered. Your registration id is "+id;
            String subject="Registration Confirmation";
            emailService.sendEmailForNewRegistration(ngo.getNgoEmail(), text, subject);
        }
        else
            throw new WomenEmpowermentException("NGO already registered!");
	}
}
