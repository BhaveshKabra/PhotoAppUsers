package org.bhavesh.photoapp.api.users.security;

import java.util.Arrays;

import org.bhavesh.photoapp.api.users.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private final Environment env;
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	public WebSecurityConfig(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = env;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		 http.csrf().disable().authorizeRequests()
		.antMatchers("/users/status/check","/actuator/**","/users","/favicon.ico","/","/h2-console/**").permitAll()
		.antMatchers(HttpMethod.POST,"/users/login").permitAll().and()
		.addFilter(getAutheticationFilter());
		return http.build();	
	}
	@Bean
	public ProviderManager authenticationManager() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userService);
		return new ProviderManager(Arrays.asList(daoAuthenticationProvider));
	}
	private AuthenticationFilter getAutheticationFilter() {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, env,authenticationManager());
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authenticationFilter;
	}
}