package id.muhariananda.notemarks.ui.note.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.AlertUtils.Companion.makeAlertToDelete
import id.muhariananda.notemarks.common.AlertUtils.Companion.makeToast
import id.muhariananda.notemarks.common.hideKeyboard
import id.muhariananda.notemarks.common.observeOnce
import id.muhariananda.notemarks.common.searchItems
import id.muhariananda.notemarks.common.swipeToDeleteItem
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.databinding.FragmentNoteListBinding
import id.muhariananda.notemarks.ui.viewmodels.NoteViewModel
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter: NoteListAdapter by lazy { NoteListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }

        setupMenu()
        showNotes()
        setupSearchView()

        hideKeyboard(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupMenu() {
        binding.toolbarNoteList.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete_all_note -> {
                    makeAlertToDelete(requireContext(), getString(R.string.text_delete_notes)) {
                        viewModel.deleteAllNotes()
                        makeToast(requireContext(), getString(R.string.text_delete_notes))
                    }
                    true
                }
                R.id.action_high_priority -> {
                    viewModel.sortByHighPriority.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                    true
                }
                R.id.action_low_priority -> {
                    viewModel.sortByLowPriority.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
    }

    private fun showNotes() {
        binding.apply {
            rvListNote.adapter = adapter
            rvListNote.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            swipeToDelete(rvListNote)
        }

        viewModel.getAllNotes.observe(viewLifecycleOwner) { notes ->
            sharedViewModel.checkNotesIfEmpty(notes)
            adapter.submitList(notes)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        recyclerView.swipeToDeleteItem { viewHolder ->
            val itemToDelete = adapter.currentList[viewHolder.adapterPosition]
            viewModel.deleteNote(itemToDelete)
            adapter.notifyItemRemoved(viewHolder.adapterPosition)
            restoreDeleteNote(viewHolder.itemView, itemToDelete)
        }
    }

    private fun restoreDeleteNote(view: View, deletedNote: Note) {
        Snackbar.make(
            view,
            getString(R.string.text_delete_item, deletedNote.title),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.text_undo)) {
                viewModel.insertData(deletedNote)
            }
            .show()
    }

    private fun setupSearchView() {
        binding.svListNote.searchItems {
            searchNote(it)
        }
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observeOnce(viewLifecycleOwner) { list ->
            list?.let {
                adapter.submitList(it)
            }
        }
    }
}