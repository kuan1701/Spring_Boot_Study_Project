package com.example.webproject.pms.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	USER,
	ADMIN;
	
	@Override
	public String getAuthority() {
		return name();
	}
}
