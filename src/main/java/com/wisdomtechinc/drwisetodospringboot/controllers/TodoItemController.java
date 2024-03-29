package com.wisdomtechinc.drwisetodospringboot.controllers;

import java.time.Instant;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;
import com.wisdomtechinc.drwisetodospringboot.repositories.TodoItemRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class TodoItemController {
    private final Logger logger = LoggerFactory.getLogger(TodoItemController.class);

    @Autowired
    private TodoItemRepository  todoItemRepository;

    @GetMapping("/")
    public ModelAndView index() {
         logger.debug( "request to GET index");
         ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("todoItems", todoItemRepository.findAll());
        modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().toString());
        return modelAndView;
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model) {
        if(todoItem == null) {
            logger.error("todoItem is null");
            return "add-todo-item";
        }
        if (result.hasErrors()) {
            logger.error("Form not valid, returning to add-todo-item");
            return "add-todo-item";
        }

        todoItem.setCreatedDate(Instant.now());
        todoItemRepository.save(todoItem);
        todoItemRepository.save(todoItem);
        return "redirect:/";
    }
    
    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, 
    @Valid TodoItem todoItem, 
    BindingResult result, 
    Model model) {
        
       if (result.hasErrors()) {
        todoItem.setId(id);
        return "update-todo-item";
       }

       todoItem.setModifiedDate(Instant.now());
       todoItemRepository.save(todoItem);
       return "redirect:/";
    }
    
    
}
