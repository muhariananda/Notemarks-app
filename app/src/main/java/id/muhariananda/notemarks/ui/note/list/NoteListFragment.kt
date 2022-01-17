package id.muhariananda.notemarks.ui.note.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.muhariananda.notemarks.common.SwipeToDelete
import id.muhariananda.notemarks.data.note.models.Note
import id.muhariananda.notemarks.databinding.FragmentNoteListBinding
import id.muhariananda.notemarks.ui.SharedViewModel
import id.muhariananda.notemarks.ui.note.NoteViewModel

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

        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvListNote.adapter = adapter
            rvListNote.layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )

            mNoteViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { notes ->
                mSharedViewModel?.checkNotesIfEmpty(notes)
                adapter.saveNotes(notes)
            })

            swipeToDelete(rvListNote)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.notesList[viewHolder.adapterPosition]
                mNoteViewModel.deleteNote(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeleteNote(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteNote(view: View, deletedNote: Note, position: Int) {
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