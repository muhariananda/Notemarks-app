package id.muhariananda.notemarks.ui.todo.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.DateHelper.Companion.convertMillisToDate
import id.muhariananda.notemarks.common.DateHelper.Companion.getDateReminderFromMillis
import id.muhariananda.notemarks.common.makeToast
import id.muhariananda.notemarks.data.entities.Todo
import id.muhariananda.notemarks.databinding.FragmentTodoAddBinding
import id.muhariananda.notemarks.ui.common.DatePickerHelper
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import id.muhariananda.notemarks.ui.viewmodels.TodoViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TodoAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTodoAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var dateMillis = 0L
    private var hour = 0
    private var minute = 0

    @Inject
    lateinit var datePickerHelper: DatePickerHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedViewModel = mSharedViewModel

        dateTimePicker()
        insertTodo()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun dateTimePicker() {
        binding.apply {
            txtAddDateTodo.setOnClickListener {
                datePickerHelper.makeDatePicker { millis ->
                    dateMillis = millis
                    txtAddDateTodo.setText(convertMillisToDate(millis))
                }
            }

            txtAddTimeTodo.setOnClickListener {
                datePickerHelper.makeTimePicker { h, m ->
                    hour = h
                    minute = m
                    txtAddTimeTodo.setText(
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

    private fun insertTodo() {
        binding.apply {
            btnAddTodo.setOnClickListener {
                val title = txtAddTitleTodo.text.toString()

                if (title.isNotBlank()) {
                    val todo = Todo(
                        0,
                        title,
                        false,
                        convertMillisToDate(dateMillis),
                        hour,
                        minute,
                        mSharedViewModel.priority.value!!
                    )
                    viewModel.insertTodo(todo)
                    setTaskReminder(todo)

                    findNavController().popBackStack()
                    makeToast(requireContext(), getString(R.string.text_success_added))
                } else {
                    makeToast(requireContext(), getString(R.string.text_message_retry))
                }
            }
        }
    }

    private fun setTaskReminder(todo: Todo) {
        binding.apply {
            val selectedDate = txtAddDateTodo.text.toString()
            val selectedTime = txtAddTimeTodo.text.toString()
            val duration = getDateReminderFromMillis(dateMillis, hour, minute)

            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                viewModel.scheduleReminder(todo.title, duration)
            }
        }
    }
}