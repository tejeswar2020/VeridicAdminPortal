package com.adminportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adminportal.domain.User;
import com.adminportal.domain.security.UserRole;
import com.adminportal.repository.UserRepository;
import com.adminportal.repository.UserRoleRepository;

@Service
public class UserSecurityService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if(null == user) 
		{
			throw new UsernameNotFoundException("Username not found");
		}

		UserRole userRole = userRoleRepository.findByUserId(user.getId());
		
		if (("ROLE_USER").equalsIgnoreCase(userRole.getRole().getName()))
		{
			throw new UsernameNotFoundException("User don't have Admin Access");
		}
		
		return user;
	}

}
