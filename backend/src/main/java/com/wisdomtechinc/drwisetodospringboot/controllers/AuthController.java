package com.wisdomtechinc.drwisetodospringboot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomtechinc.drwisetodospringboot.dtos.LoginRequest;
import com.wisdomtechinc.drwisetodospringboot.dtos.LoginResponse;
import com.wisdomtechinc.drwisetodospringboot.dtos.RegisterRequest;
import com.wisdomtechinc.drwisetodospringboot.dtos.UserResponse;
import com.wisdomtechinc.drwisetodospringboot.models.AppUser;
import com.wisdomtechinc.drwisetodospringboot.services.UserService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/v1/auth/register")
	public UserResponse register(@Valid @RequestBody RegisterRequest request) {
		AppUser user = userService.register(request.username(), request.password());
		return UserResponse.from(user);
	}
	
	@PostMapping("/api/v1/auth/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request){
		String userToken = userService.login(request.username(), request.password());
		return new LoginResponse(userToken);
	}

}
