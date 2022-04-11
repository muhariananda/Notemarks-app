package id.muhariananda.notemarks.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.muhariananda.notemarks.data.todo.ToDoRepository
import id.muhariananda.notemarks.data.db.AppNotesDatabase
import id.muhariananda.notemarks.data.entities.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = AppNotesDatabase.getDatabase(application).toDoDao()
    private val repository = ToDoRepository(todoDao)

    val todos = repository.todosFlow.asLiveData()

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodos()
        }
    }

    fun searchTodo(searchQuery: String) =
        repository.searchTodo(searchQuery).asLiveData()
}