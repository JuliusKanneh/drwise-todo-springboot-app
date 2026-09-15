
import './App.css'
import { useQuery } from '@tanstack/react-query'
import { fetchTodoItems } from './api/todos'
import AddTodoForm from './AddTodoForm';
import TodoListItem from './TodoListITem';


function App() {
  const {data, isLoading, isError, error } = useQuery({
    queryKey: ['todos'],
    queryFn: fetchTodoItems,
  });

  if (isLoading) return <p>Loading..</p>  
  if (isError) return <p>Error: {error.message}</p>

  
  return (
    <>
      <AddTodoForm />
      <h1>List of todo items</h1>
      <ul>
      {data!.map((todo) => (
        <TodoListItem key={todo.id} todo={todo} />
      ))}
    </ul>
    </>
  )
}

export default App
