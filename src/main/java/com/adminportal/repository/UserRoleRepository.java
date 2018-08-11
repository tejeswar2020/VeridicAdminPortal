package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.security.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long>
{
	UserRole findByUserId(long userId);
}
