package com.wisdomtechinc.drwisetodospringboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.repositories.TodoItemRepository;


@Component
public class TodoItemDataLoader implements CommandLineRunner{
    private final Logger logger = LoggerFactory.getLogger(TodoItemDataLoader.class);
    
    @Autowired
    TodoItemRepository todoItemRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSeedData();
        logger.debug("Loading data...");
        logger.debug("Loaded " + todoItemRepository.count() + " items");
    }

    private void loadSeedData(){
        if(todoItemRepository.count() == 0){
            TodoItem item1 = new TodoItem("Learn Spring Boot");
            TodoItem item2 = new TodoItem("Learn Spring Data JPA");
            todoItemRepository.save(item1);
            todoItemRepository.save(item2);
        }

        logger.info("Number of TodoItems in the repository: {}", todoItemRepository.count());
    }
}
