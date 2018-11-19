package com.ccc.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ccc.oauth2.dto.User;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private com.ccc.oauth2.controller.UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return  (UserDetails) userRepository.findOneByUsername(username);
	}
	
 public User addUser(User u){	
		return userRepository.save(u);
	}
 public void deleteUser(User u){
	  userRepository.delete(u);
 }

}