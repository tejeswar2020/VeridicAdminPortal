package com.adminportal.service;

import java.util.List;

import com.adminportal.domain.Ticket;

public interface TicketService 
{
	Ticket findByAssigned (String email);
	
	Ticket findByOwner (String phone);
	
	Ticket findById(Long ticketId);
	
	Ticket createTicket(Ticket ticket) throws Exception;
	
	Ticket save(Ticket ticket);
	
	List<Ticket> findAll();
	
}
