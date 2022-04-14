package id.muhariananda.notemarks.ui.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.makeToast
import id.muhariananda.notemarks.common.observeOnce
import id.muhariananda.notemarks.common.searchItems
import id.muhariananda.notemarks.common.swipeToDeleteItem
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.databinding.FragmentTodoListBinding
import id.muhariananda.notemarks.ui.common.AlertHelper
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import id.muhariananda.notemarks.ui.viewmodels.TodoViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var adapter: TodoListAdapter

    @Inject
    lateinit var alertHelper: AlertHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }

        setupMenu()
        setupSearchView()
        showAllTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupMenu() {
        binding.toolbarTodo.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_removal_todo -> {
                    confirmToRemoveAllTasks()
                    true
                }
                else -> onOptionsItemSelected(item)
            }
        }
    }

    private fun setupSearchView() {
        binding.svTodo.searchItems { newText ->
            searchTasks(newText)
        }
    }

    private fun searchTasks(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchTodo(searchQuery).observeOnce(viewLifecycleOwner) { list ->
            list?.let {
                adapter.submitList(it)
            }
        }
    }

    private fun showAllTasks() {
        adapter = TodoListAdapter { todo, isChecked ->
            val updateTodo = todo.copy(isDone = isChecked)
            viewModel.updateTodo(updateTodo)
            viewModel.todos.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }

        binding.apply {
            rvTodo.adapter = adapter
            rvTodo.layoutManager = LinearLayoutManager(requireContext())
            swipeToDelete(rvTodo)
        }

        viewModel.todos.observe(viewLifecycleOwner) { todos ->
            sharedViewModel.checkTodoIfEmpty(todos)
            adapter.submitList(todos)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        recyclerView.swipeToDeleteItem { viewHolder ->
            val itemToDelete = adapter.currentList[viewHolder.adapterPosition]
            viewModel.deleteTodo(itemToDelete)
            viewModel.cancelReminder(itemToDelete.title)
            adapter.notifyItemRemoved(viewHolder.adapterPosition)
            restoreDeleteTodo(viewHolder.itemView, itemToDelete)
        }
    }

    private fun restoreDeleteTodo(view: View, deleteItem: Todo) {
        alertHelper.makeUndoSnackBar(view, deleteItem.title) {
            viewModel.insertTodo(deleteItem)
        }
    }

    private fun confirmToRemoveAllTasks() {
        alertHelper.makeAlertToDelete(getString(R.string.text_delete_all)) {
            viewModel.deleteAllTodos()
            makeToast(requireContext(), getString(R.string.text_deleted_todos))
        }
    }
}