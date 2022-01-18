package id.muhariananda.notemarks.data

import id.muhariananda.notemarks.data.todo.ToDoDao
import id.muhariananda.notemarks.data.todo.models.ToDo
import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val toDoDao: ToDoDao) {

    val todosFlow: Flow<List<ToDo>>
        get() = toDoDao.getTodos()


    suspend fun insertTodo(toDo: ToDo) {
        toDoDao.insertTodo(toDo)
    }

    suspend fun updateTodo(toDo: ToDo) {
        toDoDao.updateTodo(toDo)
    }

    suspend fun deleteTodo(toDo: ToDo) {
        toDoDao.deleteTodo(toDo)
    }

    suspend fun deleteAllTodos() {
        toDoDao.deleteAllTodos()
    }

}