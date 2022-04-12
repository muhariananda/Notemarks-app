package id.muhariananda.notemarks

import android.app.Application
import id.muhariananda.notemarks.data.db.AppNotesDatabase

class App : Application() {
    val database: AppNotesDatabase by lazy { AppNotesDatabase.getDatabase(this) }
}