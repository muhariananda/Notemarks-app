package id.muhariananda.notemarks.ui.note

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.data.entities.Priority
import id.muhariananda.notemarks.ui.note.list.NoteListFragmentDirections

class BindingAdapters {

    companion object {
        @BindingAdapter("android:navigateToNoteAddFragment")
        @JvmStatic
        fun View.navigateToNoteAddFragment(navigate: Boolean) {
            this.setOnClickListener {
                if (navigate) {
                    this.findNavController()
                        .navigate(R.id.action_noteListFragment_to_noteAddFragment)
                }
            }
        }

        @BindingAdapter("android:sendDataToNoteUpdateFragment")
        @JvmStatic
        fun CardView.senDataToNoteUpdateFragment(currentNote: Note) {
            this.setOnClickListener {
                val action = NoteListFragmentDirections
                    .actionNoteListFragmentToNoteUpdateFragment(currentNote)
                this.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:emptyNotes")
        @JvmStatic
        fun View.emptyNotes(emptyNotes: MutableLiveData<Boolean>) {
            when (emptyNotes.value) {
                true -> this.visibility = View.VISIBLE
                else -> this.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun CardView.parsePriorityColor(priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    this.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.semantic_red
                        )
                    )
                }
                Priority.MEDIUM -> {
                    this.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.semantic_yellow
                        )
                    )
                }
                Priority.LOW -> {
                    this.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.semantic_green
                        )
                    )
                }
            }
        }
    }
}