package id.muhariananda.notemarks.ui.note

import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.data.entities.Priority
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.ui.note.list.NoteListFragmentDirections
import id.muhariananda.notemarks.ui.todo.list.TodoListFragmentDirections
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel

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
        fun View.emptyNotes(emptyNotes: LiveData<Boolean>) {
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

        @BindingAdapter("app:takeSelectedPriority")
        @JvmStatic
        fun ChipGroup.takeSelectedPriority(sharedViewModel: SharedViewModel) {
            this.setOnCheckedChangeListener { group, checkedId ->
                val titleOrNull = group.findViewById<Chip>(checkedId)?.text.toString()
                sharedViewModel.parseStringToPriority(titleOrNull)
            }
        }

        @BindingAdapter("android:navigateToAddToFragment")
        @JvmStatic
        fun FloatingActionButton.navigateToAddTodoFragment(navigate: Boolean) {
            this.setOnClickListener {
                if (navigate) {
                    this.findNavController()
                        .navigate(R.id.action_todoListFragment_to_todoAddFragment)
                }
            }
        }

        @BindingAdapter("android:sendDataToTodoUpdateFragment")
        @JvmStatic
        fun CardView.sendDataToTodoUpdateFragment(currentTodo: Todo) {
            this.setOnClickListener {
                val action = TodoListFragmentDirections
                    .actionTodoListFragmentToTodoUpdateFragment(currentTodo)
                this.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:checkTodosEmpty")
        @JvmStatic
        fun View.checkTodosEmpty(emptyTodos: LiveData<Boolean>) {
            when (emptyTodos.value) {
                true -> this.visibility = View.VISIBLE
                else -> this.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parseCheck")
        @JvmStatic
        fun CheckBox.parseCheck(todo: Todo) {
            this.isChecked = todo.isDone
        }

        @BindingAdapter("android:changeTextColorIfChecked")
        @JvmStatic
        fun TextView.changeTextColorIfChecked(todo: Todo) {
            if (todo.isDone) this.setTextColor(Color.LTGRAY)
        }
    }
}