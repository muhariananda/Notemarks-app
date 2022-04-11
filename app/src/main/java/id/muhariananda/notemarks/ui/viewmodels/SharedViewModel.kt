package id.muhariananda.notemarks.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.data.entities.Priority
import id.muhariananda.notemarks.data.entities.Todo

class SharedViewModel : ViewModel() {

    private val _emptyNotes: MutableLiveData<Boolean> = MutableLiveData(true)
    val emptyNotes: LiveData<Boolean> get() = _emptyNotes

    private val _emptyTodo: MutableLiveData<Boolean> = MutableLiveData(true)
    val emptyTodo: LiveData<Boolean> get() = _emptyTodo

    private val _priority: MutableLiveData<Priority> = MutableLiveData(Priority.LOW)
    val priority: LiveData<Priority> get() = _priority

    fun checkNotesIfEmpty(notes: List<Note>) {
        _emptyNotes.value = notes.isEmpty()
    }

    fun checkTodoIfEmpty(tasks: List<Todo>) {
        _emptyTodo.value = tasks.isEmpty()
    }

    fun validationNoteForm(title: String, content: String): Boolean {
        return !(title.isEmpty() || content.isEmpty())
    }

    fun setPriority(priority: Int) {
        when (priority) {
            1 -> _priority.value = Priority.LOW
            2 -> _priority.value = Priority.MEDIUM
            3 -> _priority.value = Priority.HIGH
        }
    }

}