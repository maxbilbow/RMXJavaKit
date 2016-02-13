package com.maxbilbow.spring;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {


	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

		Logger.getLogger(Application.class).error("Logging an error");


//		new Browser()

	}
}
