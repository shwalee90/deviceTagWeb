package com.auxil.pump;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

@SpringBootApplication
public class PumpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PumpApplication.class, args);
	}




}
