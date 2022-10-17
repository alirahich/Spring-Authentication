package com.SpringCrud;

import com.SpringCrud.Auth.entities.AppRole;
import com.SpringCrud.Auth.entities.AppUser;
import com.SpringCrud.Auth.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringCrudApplication{

	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(SpringCrudApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


}
