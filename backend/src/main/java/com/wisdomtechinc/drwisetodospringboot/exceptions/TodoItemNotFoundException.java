package com.wisdomtechinc.drwisetodospringboot.exceptions;

public class TodoItemNotFoundException extends RuntimeException {

	public TodoItemNotFoundException(Long id) {
		super("Todo item not found with id: " + id);
	}

}
