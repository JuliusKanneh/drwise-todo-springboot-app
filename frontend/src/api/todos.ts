import type { CreateTodoInput, UpdateTodoInput } from "../schema";
import type { TodoItemResponse } from "../types";

export async function fetchTodoItems(): Promise<TodoItemResponse[]> {
    const response = await fetch('api/v1/todos')
    if (!response.ok){
        throw new Error(`Failed to fetch todo items: ${response.status}`)
    }

    return response.json();
}

export async function createTodoItem(data: CreateTodoInput): Promise<TodoItemResponse> {
    const response = await fetch('/api/v1/todos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error(`Failed to create todo items: ${response.status}`);
    }

    return response.json();
}

export async function updateTodoItem(id: number, data: UpdateTodoInput): Promise<TodoItemResponse> {
    const response = await fetch(`/api/v1/todos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error(`Failed to update todo item: ${response.status}`)
    }

    return response.json();
}

export async function deleteTodoItem(id: number): Promise<void> {
    const response = await fetch(`/api/v1/todos/${id}`, {
        method: 'DELETE'
    });

    if (!response.ok) {
        throw new Error(`Failed to delete todo item: ${response.status}`);
    }
}