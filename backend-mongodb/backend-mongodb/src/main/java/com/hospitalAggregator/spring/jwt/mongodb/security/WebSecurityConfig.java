package com.hospitalAggregator.spring.jwt.mongodb.security;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hospitalAggregator.spring.jwt.mongodb.security.jwt.AuthTokenFilter;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.AdminDetailsServiceImpl;
import com.hospitalAggregator.spring.jwt.mongodb.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Configuration
    @Order(2)
	public class HospitalWebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		public HospitalWebSecurityConfig() {
            super();
        }
		@Autowired
		UserDetailsServiceImpl userDetailsServiceImpl;


		@Override
		public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
			authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
		}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/api/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.anyRequest().authenticated();

			http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		}
		
	}
	
	
	
	

	@Configuration
    @Order(1)
	public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		AdminDetailsServiceImpl adminDetailsServiceImpl;
		
		public AdminWebSecurityConfig() {
            super();
        }

		@Override
		public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
			authenticationManagerBuilder.userDetailsService(adminDetailsServiceImpl).passwordEncoder(passwordEncoder());
		}
		
		@Bean
		@Primary
		public AuthenticationManager authenticationManagerAdmin() throws Exception {
			return authenticationManager();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/api/test/**").permitAll()
				.antMatchers("/api/test/**").permitAll()
				.anyRequest().authenticated();

			http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		}
		
	}
	
	
}


