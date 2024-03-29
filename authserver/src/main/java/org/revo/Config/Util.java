package org.revo.Config;

import org.revo.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by ashraf on 17/04/17.
 */
@Configuration
public class Util {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return s -> userService.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Bean
    CommandLineRunner runner(Env env, UserService userService) {
        return strings -> {
            if (userService.count() == 0) {
                env.getUsers().forEach(userService::save);
            }
        };
    }
}
