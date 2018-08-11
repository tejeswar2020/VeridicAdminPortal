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
public class Ticket
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	private String title;
	private String scheduledState;
	private String description;
	private String owner;
	private String assigned;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getScheduledState()
	{
		return scheduledState;
	}
	
	public void setScheduledState(String scheduledState)
	{
		this.scheduledState = scheduledState;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getAssigned()
	{
		return assigned;
	}
	
	public void setAssigned(String assigned)
	{
		this.assigned = assigned;
	}

}
