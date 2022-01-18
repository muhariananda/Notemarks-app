package id.muhariananda.notemarks.ui.note

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.data.note.models.Note
import id.muhariananda.notemarks.data.note.models.Priority
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

        @BindingAdapter("android:emptyNotes")
        @JvmStatic
        fun View.emptyNotes(emptyNotes: MutableLiveData<Boolean>) {
            when (emptyNotes.value) {
                true -> this.visibility = View.VISIBLE
                else -> this.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriority")
        @JvmStatic
        fun ChipGroup.parsePriority(noteSharedViewModel: NoteSharedViewModel) {
            this.setOnCheckedChangeListener { group, checkedId ->
                val titleOrNull = group.findViewById<Chip>(checkedId)?.text.toString()
                noteSharedViewModel.parsePriority(titleOrNull)
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

        @BindingAdapter("android:sendDataToNoteUpdateFragment")
        @JvmStatic
        fun CardView.senDataToNoteUpdateFragment(currentNote: Note) {
            this.setOnClickListener {
                val action = NoteListFragmentDirections
                    .actionNoteListFragmentToNoteUpdateFragment(currentNote)
                this.findNavController().navigate(action)
            }
        }
    }
}