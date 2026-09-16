package com.wisdomtechinc.drwisetodospringboot.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * RegisterRequest
 */
public record RegisterRequest( //
		@NotBlank(message = "Username is required") String username, //
		@NotBlank(message = "Password is required") String password //
) {

}
