package com.adminportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.HR;
import com.adminportal.repository.HRRepository;
import com.adminportal.service.HRService;

@Service
public class HRServiceImpl implements HRService
{
	@Autowired
	private HRRepository hrRepository;
	
	@Override
	public List<HR> findAll()
	{
		return (List<HR>) hrRepository.findAll();
	}
	
	public List<String> getAllMailIds()
	{
		ArrayList<String> mailIds = new ArrayList<String>();//Creating arraylist  
		
		List<HR> hrList = (List<HR>) hrRepository.findAll();
		for(HR hr:hrList) 
		{
		    mailIds.add(hr.getMailId());  
		}  
		
		return mailIds;
	}
}
