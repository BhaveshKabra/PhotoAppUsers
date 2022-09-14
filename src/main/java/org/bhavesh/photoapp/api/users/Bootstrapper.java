package org.bhavesh.photoapp.api.users;

import org.bhavesh.photoapp.api.users.service.UserService;
import org.bhavesh.photoapp.api.users.shared.UserDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrapper implements CommandLineRunner {
		
	UserService userService;
	
	public Bootstrapper(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void run(String... args) throws Exception {
		UserDTO userDTO=new UserDTO();
		userDTO.setEmail("kabrabhavesh12@gmail.com");
		userDTO.setFirstName("Bhavesh");
		userDTO.setLastName("Kabra");
		userDTO.setPassword("testing");
		userService.createUser(userDTO);
	}

}
