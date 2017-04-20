package org.revo;

import org.springframework.boot.SpringApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
//@EnableEurekaClient
@EnableResourceServer
public class TubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TubeApplication.class, args);
	}
}
