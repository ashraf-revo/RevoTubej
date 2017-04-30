package org.revo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@Controller
public class UiApplication {
    private final String p = "{path:[^.]*}";

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @GetMapping(value = {p, p + "/" + p, p + "/" + p + "/" + p})
    public ModelAndView index() {
        return new ModelAndView("forward:/index.html");
    }
}
