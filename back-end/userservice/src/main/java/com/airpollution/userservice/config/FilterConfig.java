package com.airpollution.userservice.config;

import com.airpollution.userservice.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean jwtFilter() {
        System.out.println("starting userservices jwt filter");
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtFilter());
        System.out.println("adding url patterns now");
        filter.addUrlPatterns("/userservice/api/v1/profilePic/*");
        return filter;
    }
}
