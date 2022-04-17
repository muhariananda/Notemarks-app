package id.muhariananda.notemarks.ui.note.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.*
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.databinding.FragmentNoteListBinding
import id.muhariananda.notemarks.ui.common.AlertHelper
import id.muhariananda.notemarks.ui.viewmodels.NoteViewModel
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val adapter: NoteListAdapter by lazy { NoteListAdapter() }

    @Inject
    lateinit var alertHelper: AlertHelper

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
        showAllNotes()
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
                    alertHelper.makeAlertToDelete(getString(R.string.text_delete_all)) {
                        viewModel.deleteAllNotes()
                        makeToast(requireContext(), getString(R.string.text_delete_all))
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

    private fun showAllNotes() {
        binding.apply {
            rvListNote.adapter = adapter
            rvListNote.layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
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
        alertHelper.makeUndoSnackBar(view, deletedNote.title) {
            viewModel.insertData(deletedNote)
        }
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