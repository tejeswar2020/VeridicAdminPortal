package com.adminportal.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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
import com.adminportal.domain.User;
import com.adminportal.domain.security.Role;
import com.adminportal.domain.security.UserRole;
import com.adminportal.utility.SecurityUtility;
import com.adminportal.utility.MailConstructor;

@Controller
@RequestMapping("/employee")
public class EmployeeController
{
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model)
	{
		model.addAttribute("email", "");
		model.addAttribute("username", "");
		return "addEmployee";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addBookPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, Model model) throws Exception 
	{
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (employeeService.findByUsername(username) != null)
		{
			model.addAttribute("usernameExists", true);
			return "addEmployee";
		}

		if(!isEmailValid(userEmail))
		{
			model.addAttribute("emailExists", true);
			model.addAttribute("feedback", " Please check your email address !!!");
			return "addEmployee";
		}
		else if (employeeService.findByEmail(userEmail) != null)
		{
			model.addAttribute("emailExists", true);
			model.addAttribute("feedback", " Email already exists. Choose a different one. !!!");
			return "addEmployee";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		try
		{
			employeeService.createUser(user, userRoles);
		}
		catch (ConstraintViolationException cvex)
		{
			model.addAttribute("error", "true");
			model.addAttribute("feedback", "Please check your email address !!");
			return "addEmployee";
		}

		String token = UUID.randomUUID().toString();
		employeeService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://localhost:8080";

		String subject = "Veridic solutions Account created !!!";

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password, subject);

		mailSender.send(email);

		model.addAttribute("emailSent", "true");

		return "addEmployee";
	
	}

	private boolean isEmailValid(String userEmail)
	{
		if (userEmail.contains("@"))
		{
			if(userEmail.endsWith(".com") || userEmail.endsWith(".in") || userEmail.endsWith(".net") )
			{
				return true;
			}

			return false;
		}
		
		return false;
	}
	
	@RequestMapping("/employeeList")
	public String employeeList(Model model) 
	{
		List<User> employeeList = employeeService.findAll();
		model.addAttribute("employeeList", employeeList);		
		return "employeeList";
		
	}
	
	@RequestMapping("/employeeInfo")
	public String bookInfo(@RequestParam("id") Long id, Model model) 
	{
		User user = employeeService.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("workingDetailList", user.getWorkingDetailList());
		model.addAttribute("importantDateList", user.getImportantDateList());
		model.addAttribute("currentDesignation", user.getWorkingDetailList().get(0).getDesignation());
		
		return "employeeInfo";
	}
	
	/*
	
	@RequestMapping("/updateBook")
	public String updateBook(@RequestParam("id") Long id, Model model) {
		Book book = employeeService.findOne(id);
		model.addAttribute("book", book);
		
		return "updateBook";
	}
	
	@RequestMapping(value="/updateBook", method=RequestMethod.POST)
	public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
		employeeService.save(book);
		
		MultipartFile bookImage = book.getBookImage();
		
		if(!bookImage.isEmpty()) {
			try {
				byte[] bytes = bookImage.getBytes();
				String name = book.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/book/"+name));
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/book/bookInfo?id="+book.getId();
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) {
		employeeService.removeOne(Long.parseLong(id.substring(8)));
		List<Book> bookList = employeeService.findAll();
		model.addAttribute("bookList", bookList);
		
		return "redirect:/book/bookList";
	}
*/
}
