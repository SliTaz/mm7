package com.zbensoft.mmsmp.unicom.ra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.headers().frameOptions().sameOrigin();
		httpSecurity.headers().contentTypeOptions();
		httpSecurity.headers().httpStrictTransportSecurity();
		httpSecurity.headers().xssProtection();
		httpSecurity.headers().cacheControl();

	}
}
