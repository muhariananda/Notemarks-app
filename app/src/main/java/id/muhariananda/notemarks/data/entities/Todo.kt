package id.muhariananda.notemarks.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val isDone: Boolean = false,
    val date: String,
    val hour: Int,
    val minute: Int,
    val priority: Priority
) : Parcelable {
    val time: String get() {
        return "$hour:$minute"
    }
}
