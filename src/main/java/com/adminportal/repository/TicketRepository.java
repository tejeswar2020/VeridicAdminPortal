package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long>
{
	Ticket findByAssigned (String email);
	
	Ticket findByOwner (String phone);
	
	Ticket findById(Long id);
}
