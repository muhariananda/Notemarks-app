package id.muhariananda.notemarks.ui.todo.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.AlertUtils.Companion.makeToast
import id.muhariananda.notemarks.databinding.FragmentTodoUpdateBinding
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import id.muhariananda.notemarks.ui.viewmodels.TodoViewModel

@AndroidEntryPoint
class TodoUpdateFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTodoUpdateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            todo = args.currentTodo
        }

        //updateTodo()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private fun updateTodo() {
//        binding.apply {
//            btnTodoUpdate.setOnClickListener {
//                val mTitle = edtTodoUpdateTitle.text.toString()
//                if (mTitle.isNotEmpty()) {
//                    val todo = args.currentTodo.copy(title = mTitle)
//                    viewModel.updateTodo(todo)
//                    findNavController().popBackStack()
//                } else {
//                    makeToast(requireContext(), getString(R.string.text_message_retry))
//                }
//            }
//        }
//    }

}