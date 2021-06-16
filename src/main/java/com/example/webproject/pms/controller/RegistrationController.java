package com.example.webproject.pms.controller;

import com.example.webproject.pms.model.Role;
import com.example.webproject.pms.model.User;
import com.example.webproject.pms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
	
	private final UserRepo userRepo;
	
	@Autowired
	public RegistrationController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@GetMapping("/registration")
	public String registration() {
		
		return "registration";
	}
	
	@PostMapping("/registration")
	public String addUser(User user, Map<String, Object> model) {
		
		User userFromDb = userRepo.findByUsername(user.getUsername());
		
		if (userFromDb != null) {
			model.put("message", "User exist!");
			return "registration";
		}
		
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(user);
		
		return "redirect:/login";
	}
}
