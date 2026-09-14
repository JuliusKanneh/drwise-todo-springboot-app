package com.wisdomtechinc.drwisetodospringboot.dtos;

import java.time.Instant;

import com.wisdomtechinc.drwisetodospringboot.models.TodoItem;


public record TodoItemResponse(Long id, String description, boolean completed, Instant createdDate, Instant modifiedDate) {
    public static TodoItemResponse from(TodoItem item){
        return new TodoItemResponse(
            item.getId(),
            item.getDescription(),
            item.isCompleted(),
            item.getCreatedDate(),
            item.getModifiedDate()
        );
    }
}
