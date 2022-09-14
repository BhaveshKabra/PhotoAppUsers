package org.bhavesh.photoapp.api.users.service;

import org.bhavesh.photoapp.api.users.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService{
	UserDTO createUser(UserDTO userDetails);
	UserDetails findById(Long id) throws UsernameNotFoundException;
	UserDTO getUserDetailsByEmail(String email);
}
