package com.auxil.pump;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder application = new SpringApplicationBuilder(CoreApplication.class);
		application.web(WebApplicationType.NONE);

		SpringApplication.run(CoreApplication.class, args);
	}




}
