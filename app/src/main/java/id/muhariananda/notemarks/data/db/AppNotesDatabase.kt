package id.muhariananda.notemarks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.muhariananda.notemarks.common.Constant.DB_NAME
import id.muhariananda.notemarks.common.DatabaseConverter
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.data.note.NoteDao
import id.muhariananda.notemarks.data.todo.TodoDao

@Database(
    entities = [Note::class, Todo::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class AppNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun toDoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: AppNotesDatabase? = null

        fun getDatabase(context: Context): AppNotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppNotesDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}