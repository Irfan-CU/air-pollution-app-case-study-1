package com.security;

import com.airpollution.userservice.security.AuthenticationFilter;
import com.airpollution.userservice.service.UserService;
import com.airpollution.userservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter
{

        private Environment environment;
        private UserService userService;
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder)
        {
            this.environment = environment;
            this.userService = userService;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            System.out.println("inside configure websecurity");
            http.csrf().disable();
            http
                    .addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/userservice/api/v1/login/user")
                    .hasIpAddress(environment.getProperty("gateway.ip"));

                    //.and();
            http.headers().frameOptions().disable();
        }

        private AuthenticationFilter getAuthenticationFilter() throws Exception
        {
            System.out.println("inside getAuthFilter websecurity");
            AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,environment,authenticationManager());
            authenticationFilter.setAuthenticationManager(authenticationManager());
            authenticationFilter.setFilterProcessesUrl("/userservice/api/v1/login/user");

            return authenticationFilter;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        }
}
