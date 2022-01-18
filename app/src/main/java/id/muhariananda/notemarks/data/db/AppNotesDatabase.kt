package id.muhariananda.notemarks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.muhariananda.notemarks.common.Converter
import id.muhariananda.notemarks.data.note.NoteDao
import id.muhariananda.notemarks.data.note.models.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

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