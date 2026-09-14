package com.wisdomtechinc.drwisetodospringboot.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.services.TodoItemService;

@Profile("dev")
@Component
public class TodoItemDataLoader implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(TodoItemDataLoader.class);

	private final TodoItemService todoItemService;

	public TodoItemDataLoader(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	@Override
	public void run(String... args) throws Exception {
		loadSeedData();
	}

	private void loadSeedData() {
		if (todoItemService.findAll().isEmpty()) {
			logger.info("Loading seed data...");
			List<TodoItem> seedData = List.of(
					todoItemService.create("Master Spring Boot"),
					todoItemService.create("Master Spring Data JPA")
			);

			for (TodoItem todoItem : seedData) {
				logger.info("Seed data loaded: {}", todoItem);
			}
		} else {
			logger.info("The database is not empty! Skipping seed data loading.");
		}


	}

}
