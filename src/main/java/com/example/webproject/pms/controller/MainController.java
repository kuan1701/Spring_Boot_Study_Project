package com.example.webproject.pms.controller;

import com.example.webproject.pms.model.Message;
import com.example.webproject.pms.model.User;
import com.example.webproject.pms.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
	
	private final MessageRepository messageRepository;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	public MainController(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	@GetMapping("/")
	public String greeting() {
		return "greeting";
	}
	
	@GetMapping("/main")
	public String main(
			@RequestParam(required = false, defaultValue = "") String filter,
			Model model) {
		
		Iterable<Message> messages = messageRepository.findAll();
		
		if (filter!= null && !filter.isEmpty()) {
			messages = messageRepository.findByTag(filter);
		} else {
			messages = messageRepository.findAll();
		}
		
		model.addAttribute("messages", messages);
		model.addAttribute("filter", filter);
		return "main";
	}
	
	@PostMapping("/main")
	public String add(
			@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal User user,
			@RequestParam String text,
			@RequestParam String tag,
			Map<String, Object> model
	) throws IOException {
		Message message = new Message(text, tag, user);
		
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDirectory = new File(uploadPath);
			
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdir();
			}
			
			String uuidFile = UUID.randomUUID().toString();
			String resultFileName = uuidFile + "." + file.getOriginalFilename();
			
			file.transferTo(new File(uploadPath + "/" + resultFileName));
			message.setFilename(resultFileName);
		}
		messageRepository.save(message);
		
		Iterable<Message> messages = messageRepository.findAll();
		model.put("messages", messages);
		return "main";
	}
}
