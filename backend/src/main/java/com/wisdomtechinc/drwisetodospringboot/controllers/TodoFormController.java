package com.wisdomtechinc.drwisetodospringboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.services.TodoItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TodoFormController {

	private final Logger logger = LoggerFactory.getLogger(TodoFormController.class);

	private final TodoItemService todoItemService;

	public TodoFormController(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	@GetMapping("/create-todo")
	public String showCreateForm(TodoItem todoItem) {
		return "add-todo-item";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		TodoItem todoItem = todoItemService.findById(id);
		model.addAttribute("todoItem", todoItem);
		return "update-todo-item";
	}

	@GetMapping("/delete/{id}")
	public String deleteTodoItem(@PathVariable("id") long id, Model model) {
		TodoItem todoItem = todoItemService.findById(id);
		todoItemService.deleteById(id);
		logger.info("DELETED todo item with id: " + id);
		return "redirect:/";
	}

}
