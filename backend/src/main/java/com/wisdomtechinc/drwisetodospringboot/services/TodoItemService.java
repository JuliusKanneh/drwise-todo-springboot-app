package com.wisdomtechinc.drwisetodospringboot.services;

import com.wisdomtechinc.drwisetodospringboot.exceptions.TodoItemNotFoundException;
import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.repositories.TodoItemRepository;
import org.springframework.stereotype.Service;

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
		return todoItemRepository.findById(id)
			.orElseThrow(() -> new TodoItemNotFoundException(id));
	}

	public TodoItem save(TodoItem todoItem) {
		return todoItemRepository.save(todoItem);
	}

	public TodoItem update(Long id, TodoItem updatedTodoItem) {
		TodoItem existingTodoItem = findById(id);
		if (existingTodoItem != null) {
			existingTodoItem.setDescription(updatedTodoItem.getDescription());
			existingTodoItem.setCompleted(updatedTodoItem.isCompleted());
            existingTodoItem.setModifiedDate(updatedTodoItem.getModifiedDate());
			return todoItemRepository.save(existingTodoItem);
		}
		return null;
	}

	public void deleteById(Long id) {
		todoItemRepository.deleteById(id);
	}

}
