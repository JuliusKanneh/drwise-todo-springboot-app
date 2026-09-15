import { z } from 'zod'

export const createTodoSchema = z.object({
  description: z.string().min(1, 'Description is required'),
});

export const updateTodoSchema = z.object({
  description: z.string().min(1, 'Description is required'),
  completed: z.boolean()
});

export type CreateTodoInput = z.infer<typeof createTodoSchema>
export type UpdateTodoInput = z.infer<typeof updateTodoSchema>
