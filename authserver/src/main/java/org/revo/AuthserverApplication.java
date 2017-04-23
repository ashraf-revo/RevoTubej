package org.revo;

import org.revo.Config.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@RestController
@EnableJpaAuditing
//@EnableEurekaClient
@EnableConfigurationProperties(Env.class)
public class AuthserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }
}