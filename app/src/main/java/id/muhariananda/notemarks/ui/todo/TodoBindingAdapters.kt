package id.muhariananda.notemarks.ui.todo

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.data.todo.models.ToDo


class TodoBindingAdapters {
    companion object {

        @BindingAdapter("android:navigateToAddToFragment")
        @JvmStatic
        fun FloatingActionButton.navigateToAddTodoFragment(navigate: Boolean) {
            this.setOnClickListener {
                if (navigate) {
                    this.findNavController()
                        .navigate(R.id.action_toDoListFragment_to_todoAddFragment)
                }
            }
        }

        @BindingAdapter("android:parseCheck")
        @JvmStatic
        fun CheckBox.parseCheck(toDo: ToDo) {
            this.isChecked = toDo.isDone
        }
    }
}