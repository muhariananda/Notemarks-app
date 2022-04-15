package id.muhariananda.notemarks.ui.note.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.makeToast
import id.muhariananda.notemarks.data.entities.Priority
import id.muhariananda.notemarks.databinding.FragmentNoteUpdateBinding
import id.muhariananda.notemarks.ui.common.AlertHelper
import id.muhariananda.notemarks.ui.viewmodels.NoteViewModel
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import javax.inject.Inject

@AndroidEntryPoint
class NoteUpdateFragment : Fragment() {
    private var _binding: FragmentNoteUpdateBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val args by navArgs<NoteUpdateFragmentArgs>()

    @Inject
    lateinit var alertHelper: AlertHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteUpdateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            note = args.currentNote
        }

        setupUpdateMenu()
        setupChip()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUpdateMenu() {
        binding.apply {
            toolbarNoteUpdate.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toolbarNoteUpdate.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.note_update_save -> {
                        updateNote()
                        true
                    }
                    R.id.note_update_delete -> {
                        deleteNote()
                        true
                    }
                    else -> {
                        super.onOptionsItemSelected(item)
                    }
                }
            }
        }
    }

    private fun setupChip() {
        binding.apply {
            when (args.currentNote.priority) {
                Priority.LOW -> chipLowUpdate.isChecked = true
                Priority.MEDIUM -> chipMediumUpdate.isChecked = true
                Priority.HIGH -> chipHighUpdate.isChecked = true
            }
        }
    }

    private fun updateNote() {
        binding.apply {
            val title = edtNoteUpdateTitle.text.toString()
            val content = edtNoteUpdateContent.text.toString()
            val validation = sharedViewModel.validationNoteForm(title, content)

            if (validation) {
                val note = args.currentNote.copy(
                    title = title,
                    content = content,
                    priority = sharedViewModel.priority.value!!
                )
                noteViewModel.updateNote(note)
                findNavController().popBackStack()
                makeToast(requireContext(), getString(R.string.text_success_updated))
            } else {
                makeToast(requireContext(), getString(R.string.text_message_retry))
            }
        }
    }

    private fun deleteNote() {
        val title = args.currentNote.title
        alertHelper.makeAlertToDelete(
            getString(R.string.text_delete_item, title),
        ) {
            noteViewModel.deleteNote(args.currentNote)
            makeToast(requireContext(), getString(R.string.text_deleted_item, title))
            findNavController().popBackStack()
        }
    }
}