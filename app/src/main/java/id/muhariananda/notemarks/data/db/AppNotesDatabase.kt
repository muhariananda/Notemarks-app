package id.muhariananda.notemarks.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.data.note.NoteDao
import id.muhariananda.notemarks.data.todo.TodoDao

@Database(
    entities = [Note::class, Todo::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class AppNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun toDoDao(): TodoDao
}