package com.wisdomtechinc.drwisetodospringboot.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todo_items")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "description is required")
    private String description;

    @Getter
    @Setter
    private boolean completed;

    @Getter
    @Setter
    private Instant createdDate;

    @Getter
    @Setter
    private Instant modifiedDate;

    public TodoItem(){
    }

    public TodoItem(String description){
        this.description = description;
        this.completed = false;
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
    }

    @Override
    public String toString() {
        return String.format("TodoItems{id=$d, description='%s', completed='%s', createdDate='%s', modifiedDate='%s'}", id, description, completed, createdDate, modifiedDate);
    }
}
