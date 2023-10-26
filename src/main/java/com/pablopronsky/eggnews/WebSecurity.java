package com.pablopronsky.eggnews;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurity{
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
       return http.authorizeHttpRequests( auth -> {
                auth
                        .requestMatchers("/admin/*").hasRole("ADMIN")
                        .requestMatchers("/css/*","/js/*","/newUser/*").permitAll()
                        .anyRequest().authenticated();

       })
               .formLogin(login -> {
                   login
                           .loginProcessingUrl("/loginCheck")
                           .loginPage("/login")
                           .usernameParameter("email")
                           .passwordParameter("password")
                           .defaultSuccessUrl("/home/news")
                           .permitAll();
               })
               .logout(logout ->{
                   logout.logoutUrl("/logout")
                           .logoutSuccessUrl("/login")
                           .permitAll();
               })
               .build();
    }
}
