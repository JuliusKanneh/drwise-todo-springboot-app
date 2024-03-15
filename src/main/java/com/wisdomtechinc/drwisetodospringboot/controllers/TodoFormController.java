package com.wisdomtechinc.drwisetodospringboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.repositories.TodoItemRepository;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class TodoFormController {
    private final Logger logger = LoggerFactory.getLogger(TodoFormController.class);

    @Autowired
    private TodoItemRepository todoItemRepository;

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TodoItem todoItem = todoItemRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid todo item Id:" + id));
          model.addAttribute("todoItem", todoItem);
        return "update-todo-item";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") long id, Model model) {
        TodoItem todoItem = todoItemRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid todo item Id:" + id));
        todoItemRepository.delete(todoItem);
        logger.info("DELETED todo item with id: " + id);
        return "redirect:/";
    }
    
}
