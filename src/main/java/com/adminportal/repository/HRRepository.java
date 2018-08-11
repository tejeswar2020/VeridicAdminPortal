package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.HR;

public interface HRRepository extends CrudRepository<HR, Long>
{

}
