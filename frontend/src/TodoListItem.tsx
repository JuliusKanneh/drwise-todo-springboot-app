import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { updateTodoSchema, type UpdateTodoInput } from './schema'
import { updateTodoItem, deleteTodoItem } from './api/todos'
import type { TodoItemResponse } from './types'

function TodoListItem({ todo }: { todo: TodoItemResponse }) {
  const [isEditing, setIsEditing] = useState(false)
  const queryClient = useQueryClient()

  const { register, handleSubmit, formState: { errors } } = useForm<UpdateTodoInput>({
    resolver: zodResolver(updateTodoSchema),
    defaultValues: { description: todo.description, completed: todo.completed },
  })

  const updateMutation = useMutation({
    mutationFn: (data: UpdateTodoInput) => updateTodoItem(todo.id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['todos'] })
      setIsEditing(false)
    },
  })

  const deleteMutation = useMutation({
    mutationFn: () => deleteTodoItem(todo.id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['todos'] }),
  })

  if (isEditing) {
    return (
      <form onSubmit={handleSubmit((data) => updateMutation.mutate(data))}>
        <input {...register('description')} placeholder="Add a todo.." />
        {errors.description && <p>{errors.description.message}</p>}
        <label>
          <input type="checkbox" {...register('completed')} />
          Completed
        </label>
        <button type="submit">Save</button>
        <button type="button" onClick={() => setIsEditing(false)}>Cancel</button>
      </form>
    )
  } else {
    return (
      <li>
        {todo.description} {todo.completed ? '(done)' : ''}
        <button onClick={() => setIsEditing(true)}>Edit</button>
        <button onClick={() => deleteMutation.mutate()}>Delete</button>
      </li>
    )
  }
}

export default TodoListItem
