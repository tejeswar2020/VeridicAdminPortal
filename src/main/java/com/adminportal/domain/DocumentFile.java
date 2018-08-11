package com.adminportal.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class DocumentFile
{
	@Transient
	private MultipartFile cardDocument;
	
	private String cardExtention;
	
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private Ticket ticket;

	public MultipartFile getCardDocument()
	{
		return cardDocument;
	}

	public void setCardDocument(MultipartFile cardDocument)
	{
		this.cardDocument = cardDocument;
	}

	public String getCardExtention()
	{
		return cardExtention;
	}

	public void setCardExtention(String cardExtention)
	{
		this.cardExtention = cardExtention;
	}

	public Ticket getTicket()
	{
		return ticket;
	}

	public void setTicket(Ticket ticket)
	{
		this.ticket = ticket;
	}

}
