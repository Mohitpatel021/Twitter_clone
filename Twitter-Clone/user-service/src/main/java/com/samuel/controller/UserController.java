package com.samuel.controller;

import com.samuel.repository.projection.UserProfileProjection;
import com.samuel.service.AuthenticationService;
import com.samuel.service.UserServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("API/V1/USER")

public class UserController {
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private AuthenticationService authenticationService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("USER_ID")
	public UserProfileProjection getAuthenticatedUser() {
		return authenticationService.getAuthenticatedUser();
	}

	@GetMapping("ID/{email}")
	public Long getUserIdByEmail(@PathVariable("email") String email) {
		return userService.getUserIdByEmail(email);
	}

}