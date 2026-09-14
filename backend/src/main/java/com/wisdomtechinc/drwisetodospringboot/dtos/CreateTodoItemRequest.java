package com.wisdomtechinc.drwisetodospringboot.dtos;

import jakarta.validation.constraints.NotBlank;


public record CreateTodoItemRequest(@NotBlank(message="description is required") String description) {
}