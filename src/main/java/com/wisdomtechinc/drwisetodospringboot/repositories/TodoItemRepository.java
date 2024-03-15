package com.wisdomtechinc.drwisetodospringboot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long>{
    
}
