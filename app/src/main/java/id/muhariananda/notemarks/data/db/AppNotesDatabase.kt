package id.muhariananda.notemarks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.muhariananda.notemarks.common.Converter
import id.muhariananda.notemarks.data.note.NoteDao
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.data.todo.TodoDao
import id.muhariananda.notemarks.data.entities.Todo

@Database(
    entities = [Note::class, Todo::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun toDoDao(): TodoDao

    companion object {

        @Volatile
        private var INSTANCE: AppNotesDatabase? = null

        fun getDatabase(context: Context): AppNotesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppNotesDatabase::class.java,
                        "note_marks_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}