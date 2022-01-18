package id.muhariananda.notemarks.ui.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.muhariananda.notemarks.data.todo.models.ToDo
import id.muhariananda.notemarks.databinding.ItemRowToDoBinding

class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    private var todos = emptyList<ToDo>()

    class ViewHolder(private val binding: ItemRowToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDo: ToDo) {
            binding.todo = toDo
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
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun saveTodo(todoList: List<ToDo>) {
        val todoDiffUtil = ToDoDiffUtil(todos, todoList)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        this.todos = todoList
        todoDiffResult.dispatchUpdatesTo(this)
    }
}