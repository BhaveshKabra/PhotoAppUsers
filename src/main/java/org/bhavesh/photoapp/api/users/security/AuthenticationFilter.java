package org.bhavesh.photoapp.api.users.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bhavesh.photoapp.api.users.model.LoginRequestModel;
import org.bhavesh.photoapp.api.users.service.UserService;
import org.bhavesh.photoapp.api.users.shared.UserDTO;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final UserService userService;
	private final Environment env;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(UserService userService, Environment env,AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.env = env;
		this.authenticationManager=authenticationManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());
			return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth)throws IOException,ServletException
	{
		try {
		String userName=((User)auth.getPrincipal()).getUsername();
		UserDTO userdetails=userService.getUserDetailsByEmail(userName);
		SecretKeySpec secretKey = new SecretKeySpec(env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		String token=Jwts.builder().setSubject(userdetails.getUserid())
				.setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(secretKey)
				.compact();
		res.addHeader("token", token);
		res.addHeader("userid", userdetails.getUserid());
		logger.info("Token is "+token);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}