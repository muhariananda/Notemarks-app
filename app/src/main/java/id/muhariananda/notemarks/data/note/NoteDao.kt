package id.muhariananda.notemarks.data.note

import androidx.room.*
import id.muhariananda.notemarks.data.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery")
    fun searchNote(searchQuery: String): Flow<List<Note>>

    @Query(
        "SELECT * FROM note_table ORDER BY CASE " +
                "WHEN priority LIKE 'H%' THEN 1 " +
                "WHEN priority LIKE 'M%' THEN 2 " +
                "WHEN priority LIKE 'L%' THEN 3 END"
    )
    fun sortByPriorityHigh(): Flow<List<Note>>

    @Query(
        "SELECT * FROM note_table ORDER BY CASE " +
                "WHEN priority LIKE 'L%' THEN 1 " +
                "WHEN priority LIKE 'M%' THEN 2 " +
                "WHEN priority LIKE 'H%' THEN 3 END"
    )
    fun sortByPriorityLow(): Flow<List<Note>>

}