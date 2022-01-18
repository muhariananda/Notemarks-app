package id.muhariananda.notemarks.data.note

import id.muhariananda.notemarks.data.note.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao
) {

    val notesFlow: Flow<List<Note>>
        get() = noteDao.getNotes()

    val sortByHighPriority: Flow<List<Note>>
        get() = noteDao.sortByPriorityHigh()

    val sortByLowPriority: Flow<List<Note>>
        get() = noteDao.sortByPriorityLow()

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    fun searchNote(searchQuery: String) =
        noteDao.searchNote(searchQuery)

}