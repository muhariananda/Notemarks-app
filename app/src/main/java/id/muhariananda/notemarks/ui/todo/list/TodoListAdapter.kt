package id.muhariananda.notemarks.ui.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.databinding.ItemRowToDoBinding

class TodoListAdapter(
    private val onItemChecked: (Todo, Boolean) -> Unit
) : ListAdapter<Todo, TodoListAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(val binding: ItemRowToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowToDoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                val currentTodo = currentList[adapterPosition]
                onItemChecked.invoke(currentTodo, isChecked)
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}