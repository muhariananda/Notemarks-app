package id.muhariananda.notemarks.ui.todo.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhariananda.notemarks.data.todo.models.Todo
import id.muhariananda.notemarks.databinding.FragmentTodoUpdateBinding
import id.muhariananda.notemarks.ui.todo.TodoViewModel

class TodoUpdateFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTodoUpdateBinding? = null
    private val binding get() = _binding!!

    private val mTodoViewModel: TodoViewModel by viewModels()

    private val args by navArgs<TodoUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todo = args.currentTodo

        updateTodo()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateTodo() {
        binding.apply {
            btnTodoUpdate.setOnClickListener {
                val mTitle = edtTodoUpdateTitle.text.toString()
                if (mTitle.isNotEmpty()) {
                    val todo = Todo(
                        args.currentTodo.id,
                        mTitle,
                        args.currentTodo.isDone
                    )
                    mTodoViewModel.updateTodo(todo)
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Enter the task", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}