package com.adminportal.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.User;
import com.adminportal.domain.security.PasswordResetToken;
import com.adminportal.domain.security.UserRole;
import com.adminportal.repository.PasswordResetTokenRepository;
import com.adminportal.repository.RoleRepository;
import com.adminportal.repository.UserRepository;
import com.adminportal.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService
{

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public PasswordResetToken getPasswordResetToken(final String token)
	{
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token)
	{
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public User findByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByPhone(String phone)
	{
		return userRepository.findByPhone(phone);
	}

	@Override
	public User findById(Long id)
	{
		return userRepository.findOne(id);
	}

	@Override
	public User createUser(User user, Set<UserRole> userRoles)
	{
		User localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null)
		{
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else
		{
			for (UserRole ur : userRoles)
			{
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			localUser = userRepository.save(user);
		}

		return localUser;
	}

	@Override
	public User save(User user)
	{
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll()
	{
		return (List<User>) userRepository.findAll();
	}
	
	

	/*@Override
	public void updateImportantDates(User user, ImportantDate importantDate)
	{
		importantDate.setUser(user);
		user.getImportantDateList().add(importantDate);
		save(user);
	}

	@Override
	public void updateWorkingDetail(WorkingDetail workingDetail, User user)
	{
		workingDetail.setUser(user);
		user.getWorkingDetailList().add(workingDetail);
		save(user);
	}*/

}
