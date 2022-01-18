package id.muhariananda.notemarks.ui.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.muhariananda.notemarks.data.ToDoRepository
import id.muhariananda.notemarks.data.db.AppNotesDatabase
import id.muhariananda.notemarks.data.todo.models.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = AppNotesDatabase.getDatabase(application).toDoDao()
    private val repository = ToDoRepository(todoDao)

    val todos = repository.todosFlow.asLiveData()

    fun insertTodo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(toDo)
        }
    }

    fun updateTodo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(toDo)
        }
    }

    fun deleteTodo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(toDo)
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodos()
        }
    }
}