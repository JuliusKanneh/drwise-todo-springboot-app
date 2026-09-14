package com.wisdomtechinc.drwisetodospringboot.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateTodoItemRequest(@NotBlank(message = "description is required") String description,
		boolean completed) {

}
