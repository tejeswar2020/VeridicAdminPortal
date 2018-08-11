package com.adminportal.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.Ticket;
import com.adminportal.repository.TicketRepository;
import com.adminportal.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService
{
	private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public Ticket findByAssigned(String email)
	{
		return ticketRepository.findByOwner(email);
	}

	@Override
	public Ticket findByOwner(String phone)
	{
		return ticketRepository.findByOwner(phone);
	}

	@Override
	public Ticket findById(Long ticketId)
	{
		return ticketRepository.findOne(ticketId);
	}

	@Override
	public Ticket createTicket(Ticket ticket) throws Exception
	{
		Ticket currentTicket = ticketRepository.findById(ticket.getId());

		if (currentTicket != null)
		{
			LOG.info("Ticket {} already exists. Nothing will be done.", ticket.getTitle());
		} 
		else
		{
			currentTicket = ticketRepository.save(ticket);
		}

		return currentTicket;
	}

	@Override
	public Ticket save(Ticket ticket)
	{
		return ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> findAll()
	{
		return (List<Ticket>) ticketRepository.findAll();
	}

}
