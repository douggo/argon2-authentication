package com.douggo.login.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.douggo.login.infrastructure.security.filters.UserRequestFilter;

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
