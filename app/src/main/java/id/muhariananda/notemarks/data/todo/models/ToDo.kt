package id.muhariananda.notemarks.data.todo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "to_do_table")
@Parcelize
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val isDone: Boolean
) : Parcelable
