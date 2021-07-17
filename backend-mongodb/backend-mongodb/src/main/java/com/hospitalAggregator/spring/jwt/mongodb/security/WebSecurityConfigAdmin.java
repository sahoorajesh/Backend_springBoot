package com.hospitalAggregator.spring.jwt.mongodb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hospitalAggregator.spring.jwt.mongodb.security.jwt.AuthEntryPointJwt;
import com.hospitalAggregator.spring.jwt.mongodb.security.jwt.AuthTokenFilterAdmin;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.AdminDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
@Order(1)
public class WebSecurityConfigAdmin extends WebSecurityConfigurerAdapter{


	@Bean
	public AuthTokenFilterAdmin authenticationJwtTokenFilterAdmin() {
		return new AuthTokenFilterAdmin();
	}
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	AdminDetailsServiceImpl adminDetailsServiceImpl;
	
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(adminDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Primary
//	@Override
	public AuthenticationManager authenticationManageradmin() throws Exception {
		return authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/api/test/**")
		.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/test/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilterAdmin(), UsernamePasswordAuthenticationFilter.class);
	}
}
