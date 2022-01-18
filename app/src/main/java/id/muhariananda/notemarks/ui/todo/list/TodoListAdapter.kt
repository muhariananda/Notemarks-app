package id.muhariananda.notemarks.ui.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.muhariananda.notemarks.data.todo.models.Todo
import id.muhariananda.notemarks.databinding.ItemRowToDoBinding

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    var todoList = emptyList<Todo>()

    class ViewHolder(private val binding: ItemRowToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowToDoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun saveTodo(todos: List<Todo>) {
        val todoDiffUtil = TodoDiffUtil(todoList, todos)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        this.todoList = todos
        todoDiffResult.dispatchUpdatesTo(this)
    }
}