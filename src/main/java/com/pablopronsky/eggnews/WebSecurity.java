 package com.pablopronsky.eggnews;

import com.pablopronsky.eggnews.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


 @Configuration
@EnableWebSecurity
public class WebSecurity{

     @Autowired
     UserService userService;
    @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userService)
                 .passwordEncoder(new BCryptPasswordEncoder());
     }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/admin/*").hasRole("ADMIN")
                        .requestMatchers("/img/*", "/js/*", "/css/*", "/**", "/home/signup", "/home/login",
                                "/home/news")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        formLogin-> formLogin
                                .loginPage("/home/login")
                                .loginProcessingUrl("/logincheck")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/home/news")
                                .permitAll())
                .logout( logout-> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home/news").
                        permitAll() )
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults());
        return http.build();
    }
    }


