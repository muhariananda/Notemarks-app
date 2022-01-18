package id.muhariananda.notemarks.ui.note.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.muhariananda.notemarks.data.note.models.Note
import id.muhariananda.notemarks.databinding.ItemRowNoteBinding

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.MyViewHolder>() {

    var notesList = emptyList<Note>()

    class MyViewHolder(private val binding: ItemRowNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowNoteBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun saveNotes(notes: List<Note>) {
        val noteDiffUtil = NoteDiffUtil(notesList, notes)
        val noteDiffResult = DiffUtil.calculateDiff(noteDiffUtil)
        this.notesList = notes
        noteDiffResult.dispatchUpdatesTo(this)
    }
}