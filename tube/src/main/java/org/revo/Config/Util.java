package org.revo.Config;

import org.revo.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Created by ashraf on 18/04/17.
 */
@Configuration
public class Util {
    @Bean
    public AuditorAware<Long> aware(UserService userService) {
        return userService::current;
    }
}
