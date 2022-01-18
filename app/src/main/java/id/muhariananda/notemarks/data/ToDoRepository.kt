package id.muhariananda.notemarks.data

import id.muhariananda.notemarks.data.todo.TodoDao
import id.muhariananda.notemarks.data.todo.models.Todo
import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val todoDao: TodoDao) {

    val todosFlow: Flow<List<Todo>>
        get() = todoDao.getTodos()


    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    suspend fun deleteAllTodos() {
        todoDao.deleteAllTodos()
    }

    fun searchTodo(searchQuery: String) =
        todoDao.searchTodo(searchQuery)

}