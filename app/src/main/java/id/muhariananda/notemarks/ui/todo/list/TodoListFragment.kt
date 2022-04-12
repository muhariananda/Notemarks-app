package id.muhariananda.notemarks.ui.todo.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.SwipeToDelete
import id.muhariananda.notemarks.common.observeOnce
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.databinding.FragmentTodoListBinding
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import id.muhariananda.notemarks.ui.viewmodels.TodoViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var adapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedViewModel = sharedViewModel

        setupRecyclerView()
        setupMenu()
        setupSearchView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupMenu() {
        binding.toolbarTodo.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_removal_todo -> {
                    confirmRemoval()
                    true
                }
                else -> onOptionsItemSelected(item)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = TodoListAdapter { todo, isChecked ->
            val updateTodo = todo.copy(isDone = isChecked)
            viewModel.updateTodo(updateTodo)
        }

        binding.apply {
            rvTodo.adapter = adapter
            rvTodo.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            swipeToDelete(rvTodo)
        }

        viewModel.todos.observe(viewLifecycleOwner) { todos ->
            sharedViewModel.checkTodoIfEmpty(todos)
            adapter.submitList(todos)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteTodo(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeleteTodo(viewHolder.itemView, itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteTodo(view: View, deleteItem: Todo) {
        val snackBar = Snackbar.make(
            view,
            "Delete ${deleteItem.title}",
            Snackbar.LENGTH_LONG
        )
        snackBar.apply {
            setAction("Undo") {
                viewModel.insertTodo(deleteItem)
            }
        }.show()
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setNegativeButton("No") { _, _ -> }
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllTodos()
                Toast.makeText(
                    requireContext(),
                    "Successfully to remove all todos",
                    Toast.LENGTH_LONG
                ).show()
            }
            setTitle("Delete All Todos")
            setMessage("Are you sure want to delete all?")
        }.show()
    }

    private fun setupSearchView() {
        binding.svTodo.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchTodo(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchTodo(it) }
                return true
            }
        })
    }

    private fun searchTodo(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchTodo(searchQuery).observeOnce(viewLifecycleOwner) { list ->
            list?.let {
                adapter.submitList(it)
            }
        }
    }
}