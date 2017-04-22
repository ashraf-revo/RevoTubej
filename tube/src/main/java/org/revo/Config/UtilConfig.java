package org.revo.Config;

import org.revo.Service.UserService;
import org.revo.Util.Hls;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.AuditorAware;

import java.io.File;

/**
 * Created by ashraf on 18/04/17.
 */
@Configuration
public class UtilConfig {
    @Bean
    CommandLineRunner runner() {
        return args -> {
            File bento4 = new ClassPathResource("Bento4").getFile();
            Hls.init(bento4);
        };
    }

    @Bean
    public AuditorAware<Long> aware(UserService userService) {
        return userService::current;
    }
}
