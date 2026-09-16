package com.wisdomtechinc.drwisetodospringboot.dtos;

import com.wisdomtechinc.drwisetodospringboot.models.AppUser;

public record UserResponse(Long id, String username) {

	public static UserResponse from(AppUser user) {
		return new UserResponse(user.getId(), user.getUsername());
	}
}
