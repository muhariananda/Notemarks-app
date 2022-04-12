package id.muhariananda.notemarks.ui.note.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.SwipeToDelete
import id.muhariananda.notemarks.common.hideKeyboard
import id.muhariananda.notemarks.common.observeOnce
import id.muhariananda.notemarks.data.entities.Note
import id.muhariananda.notemarks.databinding.FragmentNoteListBinding
import id.muhariananda.notemarks.ui.viewmodels.NoteViewModel
import id.muhariananda.notemarks.ui.viewmodels.SharedViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val mNoteViewModel: NoteViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

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

        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        setupMenu()
        setupRecyclerView()
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
                    confirmRemoval()
                    true
                }
                R.id.action_high_priority -> {
                    mNoteViewModel.sortByHighPriority.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                    true
                }
                R.id.action_low_priority -> {
                    mNoteViewModel.sortByLowPriority.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
    }

    private fun setupSearchView() {
        binding.svListNote.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchNote(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { searchNote(it) }
                    return true
                }
            }
        )
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query%"
        mNoteViewModel.searchNote(searchQuery).observeOnce(viewLifecycleOwner) { list ->
            list?.let {
                adapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvListNote.adapter = adapter
            rvListNote.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            swipeToDelete(rvListNote)
        }

        mNoteViewModel.getAllNotes.observe(viewLifecycleOwner) { notes ->
            mSharedViewModel.checkNotesIfEmpty(notes)
            adapter.submitList(notes)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.currentList[viewHolder.adapterPosition]
                mNoteViewModel.deleteNote(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeleteNote(viewHolder.itemView, itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteNote(view: View, deletedNote: Note) {
        val snackBar = Snackbar.make(
            view,
            "Deleted ${deletedNote.title}",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mNoteViewModel.insertData(deletedNote)
        }
        snackBar.show()
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setNegativeButton("No") { _, _ -> }
            setPositiveButton("Yes") { _, _ ->
                mNoteViewModel.deleteAllNotes()
                Toast.makeText(
                    requireContext(),
                    "Successfully to remove all",
                    Toast.LENGTH_LONG
                ).show()
            }
            setTitle("Delete Notes")
            setMessage("Are you sure want to delete?")
        }.show()
    }
}