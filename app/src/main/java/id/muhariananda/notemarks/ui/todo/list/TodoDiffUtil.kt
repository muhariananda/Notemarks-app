package id.muhariananda.notemarks.ui.todo.list

import androidx.recyclerview.widget.DiffUtil
import id.muhariananda.notemarks.data.entities.Todo

class TodoDiffUtil(
    private val oldList: List<Todo>,
    private val newList: List<Todo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].isDone == newList[newItemPosition].isDone
    }
}