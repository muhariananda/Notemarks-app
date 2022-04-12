package id.muhariananda.notemarks.ui.todo.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.AlertUtils
import id.muhariananda.notemarks.common.AlertUtils.Companion.makeToast
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.databinding.FragmentTodoAddBinding
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import id.muhariananda.notemarks.ui.viewmodels.TodoViewModel

@AndroidEntryPoint
class TodoAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTodoAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }
        //insertTodo()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private fun insertTodo() {
//        binding.apply {
//            btnTodoAdd.setOnClickListener {
//                val mTitle = edtTodoAddTitle.text.toString()
//                if (mTitle.isNotEmpty()) {
//                    val todo = Todo(
//                        0,
//                        mTitle
//                    )
//                    viewModel.insertTodo(todo)
//                    findNavController().popBackStack()
//                    makeToast(requireContext(), getString(R.string.text_success_added))
//                } else {
//                    makeToast(requireContext(), getString(R.string.text_message_retry))
//                }
//            }
//        }
//    }

}