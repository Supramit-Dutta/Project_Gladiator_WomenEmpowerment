package com.lti.womenempowerment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lti"})
@EntityScan(basePackages = "com.lti")
public class WomenEmpowermentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WomenEmpowermentApplication.class, args);
		System.out.println("This is Women Empowerment Project");
	}

}
