package id.muhariananda.notemarks.ui.todo.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.DateHelper
import id.muhariananda.notemarks.common.makeToast
import id.muhariananda.notemarks.data.entities.Priority
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.databinding.FragmentTodoUpdateBinding
import id.muhariananda.notemarks.ui.common.DatePickerHelper
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import id.muhariananda.notemarks.ui.viewmodels.TodoViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TodoUpdateFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTodoUpdateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val args by navArgs<TodoUpdateFragmentArgs>()

    private var dateMillis = 0L
    private var hour = 0
    private var minute = 0

    @Inject
    lateinit var datePickerHelper: DatePickerHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            sharedViewModel = mSharedViewModel
            todo = args.currentTodo
        }

        setupChip()
        dateTimePicker()
        updateTodo()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupChip() {
        binding.apply {
            when (args.currentTodo.priority) {
                Priority.LOW -> chipLowUpdate.isChecked = true
                Priority.MEDIUM -> chipMediumUpdate.isChecked = true
                Priority.HIGH -> chipHighUpdate.isChecked = true
            }

        }
    }

    private fun dateTimePicker() {
        binding.apply {
            txtDate.setOnClickListener {
                datePickerHelper.makeDatePicker { millis ->
                    dateMillis = millis
                    txtDate.setText(DateHelper.convertMillisToDate(millis))
                }
            }

            txtTime.setOnClickListener {
                datePickerHelper.makeTimePicker { h, m ->
                    hour = h
                    minute = m
                    txtTime.setText(
                        getString(
                            R.string.text_show_time,
                            h.toString(),
                            m.toString()
                        )
                    )
                }
            }
        }
    }

    private fun updateTodo() {
        binding.apply {
            btnUpdateTodo.setOnClickListener {
                val mTitle = txtDate.text.toString()
                if (mTitle.isNotEmpty()) {
                    val todo = args.currentTodo.copy(title = mTitle)
                    viewModel.updateTodo(todo)
                    setTaskReminder(todo, args.currentTodo)
                    findNavController().popBackStack()
                } else {
                    makeToast(requireContext(), getString(R.string.text_message_retry))
                }
            }
        }
    }

    private fun setTaskReminder(newTodo: Todo, oldTodo: Todo) {
        binding.apply {
            val selectedDate = txtDate.text.toString()
            val selectedTime = txtTime.text.toString()
            val duration = DateHelper.getDateReminderFromMillis(dateMillis, hour, minute)

            if (selectedDate != oldTodo.date && selectedTime != oldTodo.time) {
                viewModel.cancelReminder(oldTodo.time)
                viewModel.scheduleReminder(newTodo.title, duration)
            }
        }
    }
}
