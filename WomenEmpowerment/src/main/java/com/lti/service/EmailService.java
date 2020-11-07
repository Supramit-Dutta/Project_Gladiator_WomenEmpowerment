package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private MailSender mailSender;
	
    public void sendEmailForNewRegistration(String email,String text,String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("organization.weempoweryou@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    public void sendEmailForOTP(String email,String text,String subject) {
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("organization.weempoweryou@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
