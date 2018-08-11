package com.adminportal.service;

import java.util.List;

import com.adminportal.domain.HR;

public interface HRService
{
	List<HR> findAll();
	
	List<String> getAllMailIds();
}
