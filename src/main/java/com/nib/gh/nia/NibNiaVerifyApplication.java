package com.nib.gh.nia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NibNiaVerifyApplication {

	public  static void main(String[] args) {
		SpringApplication.run(NibNiaVerifyApplication.class, args);
	}

}