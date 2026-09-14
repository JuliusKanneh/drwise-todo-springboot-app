package com.wisdomtechinc.drwisetodospringboot.services;

import com.wisdomtechinc.drwisetodospringboot.exceptions.TodoItemNotFoundException;
import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.repositories.TodoItemRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoItemService {

	private final TodoItemRepository todoItemRepository;

	// Constructor injection is preferred over field injection for better testability and
	// immutability.
	public TodoItemService(TodoItemRepository todoItemRepository) {
		this.todoItemRepository = todoItemRepository;
	}

	// Logics go here.
	public List<TodoItem> findAll() {
		ArrayList<TodoItem> todoItems = new ArrayList<>();
		todoItemRepository.findAll().forEach(todoItems::add);
		return todoItems;
	}

	public TodoItem findById(Long id) {
		return todoItemRepository.findById(id).orElseThrow(() -> new TodoItemNotFoundException(id));
	}

	public TodoItem create(String description) {
		TodoItem newTodoItem = new TodoItem(description);
		return todoItemRepository.save(newTodoItem);
	}

	public TodoItem update(Long id, String description, boolean completed) {
		TodoItem existingTodoItem = findById(id);
		if (existingTodoItem != null) {
			existingTodoItem.setDescription(description);
			existingTodoItem.setCompleted(completed);
			existingTodoItem.setModifiedDate(Instant.now());

			return todoItemRepository.save(existingTodoItem);
		}
		return null;
	}

	public void deleteById(Long id) {
		todoItemRepository.deleteById(id);
	}

}
