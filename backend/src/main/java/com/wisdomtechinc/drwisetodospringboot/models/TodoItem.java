package com.wisdomtechinc.drwisetodospringboot.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "todo_items")
public class TodoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@NotBlank(message = "description is required")
	private String description;

	private boolean completed;

	private Instant createdDate;

	private Instant modifiedDate;

	public TodoItem() {
	}

	public TodoItem(String description) {
		this.description = description;
		this.completed = false;
		this.createdDate = Instant.now();
		this.modifiedDate = Instant.now();
	}

	@Override
	public String toString() {
		return String.format("TodoItems{id=%d, description='%s', completed='%s', createdDate='%s', modifiedDate='%s'}",
				id, description, completed, createdDate, modifiedDate);
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
