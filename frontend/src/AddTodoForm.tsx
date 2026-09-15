import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { createTodoSchema, type CreateTodoInput } from './schema'
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createTodoItem } from './api/todos';

function AddTodoForm() {

    const queryClient = useQueryClient()
    const { register, handleSubmit, reset, formState: { errors } } = useForm<CreateTodoInput>({
        resolver: zodResolver(createTodoSchema),
    })

    const mutation = useMutation({
        mutationFn: createTodoItem,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['todos'] })
            reset()
        },
    })

    function onSubmit(data: CreateTodoInput) {
        mutation.mutate(data)
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <input {...register('description')} placeholder='Add a todo..' />
            {errors.description && <p>{errors.description.message}</p>}
            <button type='submit'>Add</button>
        </form>
    )
    
}

export default AddTodoForm