import type { TodoItemResponse } from "../types";

export async function fetchTodoItems(): Promise<TodoItemResponse[]> {
    const response = await fetch('api/v1/todos')
    if (!response.ok){
        throw new Error('Failed to fetch todo items: ${response.status}')
    }

    return response.json();
}