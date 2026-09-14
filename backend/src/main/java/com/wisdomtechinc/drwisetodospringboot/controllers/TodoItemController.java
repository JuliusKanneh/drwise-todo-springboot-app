package com.wisdomtechinc.drwisetodospringboot.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wisdomtechinc.drwisetodospringboot.dtos.CreateTodoItemRequest;
import com.wisdomtechinc.drwisetodospringboot.dtos.TodoItemResponse;
import com.wisdomtechinc.drwisetodospringboot.dtos.UpdateTodoItemRequest;
import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.services.TodoItemService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class TodoItemController {

	private final Logger logger = LoggerFactory.getLogger(TodoItemController.class);

	private final TodoItemService todoItemService;

	public TodoItemController(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}
	
	@GetMapping("/api/v1/todos")
	public List<TodoItemResponse> getAllTodoItems() {
		List<TodoItem> all = todoItemService.findAll();
		List<TodoItemResponse> allReponse = new ArrayList<>();
		
		for (TodoItem todoItem : all) {
			allReponse.add(TodoItemResponse.from(todoItem));
		}
		return allReponse;
	}

	@GetMapping("/api/v1/todos/{id}")
	public TodoItemResponse getTodoItemById(@PathVariable("id") Long id) {
		TodoItem todoItem = todoItemService.findById(id);
		return TodoItemResponse.from(todoItem);
	}
	
	@PostMapping("/api/v1/todos")
	public TodoItemResponse createTodoItem(@RequestBody @Valid CreateTodoItemRequest request) {
		TodoItem todoItem = todoItemService.create(request.description());
		return TodoItemResponse.from(todoItem);
	}

	@PutMapping("/api/v1/todos/{id}")
	public TodoItemResponse updateTodoItem(@Valid @RequestBody UpdateTodoItemRequest request, @PathVariable("id") Long id) {
		TodoItem todoItem = todoItemService.update(id, request.description(), request.completed());
		return TodoItemResponse.from(todoItem);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping ("/api/v1/todos/{id}")
	public void deleteTodoItem(@PathVariable("id") Long id) {
		todoItemService.deleteById(id);
	}

}
