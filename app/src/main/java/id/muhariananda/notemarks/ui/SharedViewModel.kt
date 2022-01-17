package id.muhariananda.notemarks.ui

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.muhariananda.notemarks.data.note.models.Note
import id.muhariananda.notemarks.data.note.models.Priority
import java.text.SimpleDateFormat
import java.util.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyNotes: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkNotesIfEmpty(notes: List<Note>) {
        emptyNotes.value = notes.isEmpty()
    }

    fun validationNoteForm(title: String, content: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            false
        } else !(title.isEmpty() || content.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High priority" -> Priority.HIGH
            "Medium priority" -> Priority.MEDIUM
            "Low priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }

    fun getCurrentDate(): String {
        val current = Date()
        val formatter = SimpleDateFormat("EEE, k:mm", Locale.getDefault())
        return formatter.format(current)
    }

}