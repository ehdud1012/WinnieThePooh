package com.kdy.nwtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class WinnieThePoohApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinnieThePoohApplication.class, args);
	}

}
