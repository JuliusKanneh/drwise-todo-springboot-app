
import './App.css'
import { useQuery } from '@tanstack/react-query'
import { fetchTodoItems } from './api/todos'


function App() {
  const {data, isLoading, isError, error } = useQuery({
    queryKey: ['todos'],
    queryFn: fetchTodoItems,
  });

  if (isLoading) return <p>Loading..</p>  
  if (isError) return <p>Erro: {error.message}</p>

  
  return (
    <>
      <h1>List of todo items</h1>
      <ul>
      {data!.map((todo) => (
        <li key={todo.id}>{todo.description}</li>
      ))}
    </ul>
    </>
  )
}

export default App
