package org.revo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@EnableResourceServer
@EnableJpaAuditing
public class TubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TubeApplication.class, args);
    }
}
