package id.muhariananda.notemarks.ui.todo

import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.ui.todo.list.TodoListFragmentDirections


class TodoBindingAdapters {
    companion object {

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