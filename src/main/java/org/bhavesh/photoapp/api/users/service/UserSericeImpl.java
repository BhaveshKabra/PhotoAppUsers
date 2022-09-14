package org.bhavesh.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.bhavesh.photoapp.api.users.data.UserRepoistory;
import org.bhavesh.photoapp.api.users.data.UsersEntity;
import org.bhavesh.photoapp.api.users.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSericeImpl implements UserService {
	
	private final UserRepoistory userRepoistory;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ModelMapper modelMapper;
	public UserSericeImpl(UserRepoistory userRepoistory,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepoistory = userRepoistory;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		this.modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@Override
	public UserDTO createUser(UserDTO userDetails) {
		userDetails.setUserid(UUID.randomUUID().toString());
		System.out.println(userDetails.getPassword());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		UsersEntity userEntity=modelMapper.map(userDetails, UsersEntity.class);
		userRepoistory.save(userEntity);
		return modelMapper.map(userEntity,UserDTO.class);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			UsersEntity userEntity=userRepoistory.findByEmail(username);
			if(userEntity== null) throw new UsernameNotFoundException(username);
			return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
	}
	@Override
	public UserDetails findById(Long id) throws UsernameNotFoundException{
			UsersEntity userEntity=userRepoistory.findById(id).orElse(null);
			if(userEntity == null) throw new UsernameNotFoundException("With id as "+id);
			return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UsersEntity userEntity=userRepoistory.findByEmail(email);
		if(userEntity== null) throw new UsernameNotFoundException(email);
		User user=new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
		return modelMapper.map(user, UserDTO.class);
	}
}
