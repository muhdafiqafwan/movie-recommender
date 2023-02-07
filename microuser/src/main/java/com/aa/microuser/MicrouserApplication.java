package com.aa.microuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MicrouserApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicrouserApplication.class, args);
	}

	// A bean of BCryptPasswordEncoder to be use in Service
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
