package id.muhariananda.notemarks.data.note.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.muhariananda.notemarks.data.note.models.Priority
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var content: String,
    var date: String,
    var priority: Priority
): Parcelable
