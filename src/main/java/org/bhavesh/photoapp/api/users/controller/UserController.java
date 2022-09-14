package org.bhavesh.photoapp.api.users.controller;

import javax.validation.Valid;

import org.bhavesh.photoapp.api.users.model.CreateUserResponseModel;
import org.bhavesh.photoapp.api.users.model.UserRequestModel;
import org.bhavesh.photoapp.api.users.service.UserService;
import org.bhavesh.photoapp.api.users.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final Environment env;
	private final UserService userService;

	public UserController(Environment env, UserService userService) {
		this.env = env;
		this.userService = userService;
	}
	@GetMapping("/status/check")
	public String status() {
		return "UP with port " + env.getProperty("local.server.port");
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody UserRequestModel userRequestModel)
	{
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO=modelMapper.map(userRequestModel, UserDTO.class);
		UserDTO returnDTO=userService.createUser(userDTO);
		CreateUserResponseModel userResponse=modelMapper.map(returnDTO, CreateUserResponseModel.class);
		System.out.println(returnDTO.getEncryptedPassword());
		System.out.println(returnDTO.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse); 
	}
	@GetMapping("/fetch/{id}")
	public ResponseEntity<CreateUserResponseModel> getById(@RequestParam Long id) {
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO=modelMapper.map(userService.findById(id),UserDTO.class);
		CreateUserResponseModel responseModel=modelMapper.map(userDTO,CreateUserResponseModel.class);
		return new ResponseEntity<>(responseModel,HttpStatus.ACCEPTED); 
	}
}