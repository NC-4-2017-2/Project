package com.netcracker.project.config.security;

import com.netcracker.project.services.impl.SecurityUserServiceImpl;
import com.netcracker.project.model.SecurityUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = false)
@ImportResource("classpath:Spring-SecurityUser.xml")
@ComponentScan("com.netcracker.project")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityUserDAO securityUserDAO;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(new SecurityUserServiceImpl(securityUserDAO))
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/WEB-INF/**").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf().disable()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true);
  }

  @Bean
  public ShaPasswordEncoder passwordEncoder() {
    return new ShaPasswordEncoder();
  }
}