package com.example.webproject.pms.repository;

import com.example.webproject.pms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}
