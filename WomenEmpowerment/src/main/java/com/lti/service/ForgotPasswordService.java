package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.repository.EmpowermentRepository;

@Service
public class ForgotPasswordService {

	@Autowired
	private EmpowermentRepository repo;
	
	public int setUserPassword(String email,String password) {
		int check=repo.updateUserPassword(email, password);
		return check;
	}
	public int setNGOPassword(String email,String password) {
		int check=repo.updateNGOPassword(email, password);
		return check;
	}
	public int setAdminPassword(String email,String password) {
		int check=repo.updateAdminPassword(email,password);
		return check;
	}
}
