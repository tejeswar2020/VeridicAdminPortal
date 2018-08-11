package com.adminportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * copyrights © 2018 - Veridic solutions
 * 
 * @author Tejeswar Velpucharla
 */

@Entity
public class HR
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	private String mailId;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getMailId()
	{
		return mailId;
	}
	public void setMailId(String mailId)
	{
		this.mailId = mailId;
	}

}
