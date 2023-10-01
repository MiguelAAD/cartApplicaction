package com.miguelandaluz.oneboxcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.miguelandaluz.oneboxcart.service.WelcomeService;
import com.miguelandaluz.oneboxcart.service.impl.WelcomeServiceImpl;

@SpringBootApplication
public class OneboxcartApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneboxcartApplication.class, args);
		WelcomeService welcomeService = new WelcomeServiceImpl();
		welcomeService.welcome();
	}
}
