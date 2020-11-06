package com.lti.service;

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
}
