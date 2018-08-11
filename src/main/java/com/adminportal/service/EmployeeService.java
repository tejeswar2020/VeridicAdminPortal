package com.adminportal.service;

import java.util.List;
import java.util.Set;

import com.adminportal.domain.User;
import com.adminportal.domain.security.PasswordResetToken;
import com.adminportal.domain.security.UserRole;

public interface EmployeeService 
{
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User findByPhone (String phone);
	
	User findById(Long id);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	List<User> findAll();
	
//	void updateImportantDates(User user, ImportantDate importantDate);
	
//	void updateWorkingDetail(WorkingDetail workingDetail, User user);
	
}
