package id.muhariananda.notemarks.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.data.todo.ToDoRepository
import id.muhariananda.notemarks.ui.worker.TaskReminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val taskReminder: TaskReminder
) : ViewModel() {

    val todos = repository.todosFlow.asLiveData()

    fun scheduleReminder(task: String, duration: Long) {
        taskReminder.setTaskReminder(task, duration)
    }

    fun cancelReminder(task: String) {
        taskReminder.cancelTaskReminder(task)
    }

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