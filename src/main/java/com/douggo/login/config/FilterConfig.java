package com.douggo.login.config;

import com.douggo.login.infrastructure.security.filters.UserRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UserRequestFilter> userRequestFilter() {
        FilterRegistrationBean<UserRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserRequestFilter());
        registrationBean.addUrlPatterns("/users/create");
        return registrationBean;
    }

}
