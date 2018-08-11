package com.adminportal.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adminportal.service.EmployeeService;
import com.adminportal.service.HRService;
import com.adminportal.service.TicketService;
import com.adminportal.utility.MailConstructor;
import com.adminportal.domain.Ticket;
import com.adminportal.domain.User;

@Controller
@RequestMapping("/ticket")
public class TicketController
{
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private HRService hrService;

	private static StringBuilder feedBack =  new StringBuilder();
	
	private static String EMPTY = " should not be empty.";

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createTicket(Model model)
	{
		Ticket ticket = new Ticket();
		model.addAttribute("ticket", ticket);
		
		List<String> mailIds = hrService.getAllMailIds();
		model.addAttribute("mailIdList", mailIds);
		
		model.addAttribute("invalidTicket", false);
		model.addAttribute("feedback", "");
		
		return "createTicket";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createTicketPost(HttpServletRequest request, @ModelAttribute("Ticket") Ticket ticket, @RequestParam("id") Long id, Principal principal,
			Model model) throws Exception
	{
		Ticket currentTicket = new Ticket();
		
		if(isTicketValid(model, ticket))
		{
			model.addAttribute("ticket", ticket);
			
			model.addAttribute("invalidTicket", true);
			model.addAttribute("feedBack", feedBack);
			
			return "createTicket";
		}
		
		if(null == id)
		{
			try
			{
				currentTicket = ticketService.createTicket(ticket);
			}
			catch (Exception ex)
			{
				model.addAttribute("error", "true");
				model.addAttribute("feedback", "Please check your details !!");
				return "addEmployee";
			}
		}
		else
		{
			currentTicket = ticketService.save(ticket);
		}
		
		// http://localhost:8081/adminportal/ticket/ticketInfo?id=5
		String appUrl = "http://localhost:8081" + request.getContextPath() + "/ticket/ticketInfo?id=" + currentTicket.getId();

		String subject = "Ticket " + currentTicket.getId() + " " + currentTicket.getScheduledState() + " " + currentTicket.getTitle();
		
		String content = "Title " + currentTicket.getTitle() + "\n Description: " + currentTicket.getDescription();

		User user = employeeService.findByEmail(currentTicket.getAssigned());
		
		sendEmailOrMobileAlert(request, appUrl, subject, content, user);

		return "redirect:ticketList";
	}

	private boolean isTicketValid(Model model, Ticket ticket)
	{
		feedBack.setLength(0);
		if (ticket.getTitle().isEmpty())
		{
			feedBack.append("<p><span class=\"glyphicon glyphicon-check\"></span>&nbsp;Title " + EMPTY + "</p>");
		}
		
		if (null == ticket.getAssigned())
		{
			feedBack.append("<p><span class=\"glyphicon glyphicon-check\"></span>&nbsp;Assigned " + EMPTY + "</p>");
		}
		
		if (null == ticket.getOwner())
		{
			feedBack.append("<p><span class=\"glyphicon glyphicon-check\"></span>&nbsp;Owner " + EMPTY + "</p>");
		}
		
		if(!feedBack.toString().equals(""))
		{
			return true;
		}
		
		return false;
	}
	
	@RequestMapping("/ticketList")
	public String ticketList(Model model) 
	{
		List<Ticket> ticketList = ticketService.findAll();
		model.addAttribute("ticketList", ticketList);		
		return "ticketList";
	}
	
	@RequestMapping("/ticketInfo")
	public String ticketInfo(@RequestParam("id") Long id, Model model) 
	{
		Ticket ticket = ticketService.findById(id);
		model.addAttribute("ticket", ticket);
		
		List<String> mailIds = hrService.getAllMailIds();
		model.addAttribute("mailIdList", mailIds);
		
		return "createTicket";
	}
	
	
	public boolean sendEmailOrMobileAlert(HttpServletRequest request, 
			String appUrl,
			String subject,
			String content,
			User user)
	{
	
		SimpleMailMessage email = mailConstructor.sendEmail(appUrl, request.getLocale(), user, subject, content);
		
		mailSender.send(email);
		
		return true;
	}
	
	/*@RequestMapping("/uploadfile")
	public String uploadFileForTicket(Model model, @ModelAttribute("DocumentFile") DocumentFile documentFile ) 
	{
		
		MultipartFile cardDocument = documentFile.getCardDocument();
		String cardDocumentExtention = FeatureCatalog.getExtensionOfFile(cardDocument);
		System.out.println("Original FileName " + cardDocument.getOriginalFilename());
		System.out.println("Original contentType " + cardDocument.getContentType());
		
		List<Ticket> ticketList = ticketService.findAll();
		model.addAttribute("ticketList", ticketList);		
		return "ticketList";
	}*/

}