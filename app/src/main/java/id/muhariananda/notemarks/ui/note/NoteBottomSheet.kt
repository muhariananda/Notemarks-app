package id.muhariananda.notemarks.ui.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import id.muhariananda.notemarks.databinding.FragmentNoteBottomDialogBinding

class NoteBottomSheet: BottomSheetDialogFragment() {

    private var _binding: FragmentNoteBottomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBottomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        passData()
        _binding = null
    }

    private fun passData() {
        var selectedChip = ""
        binding.chipChoicePriority.setOnCheckedChangeListener { group, checkedId ->
            val titleOrNull = group.findViewById<Chip>(checkedId)?.text.toString()
            selectedChip = titleOrNull ?: "Low"
            Log.d("SELECTED_CHIP", selectedChip)
            Toast.makeText(activity, selectedChip, Toast.LENGTH_LONG).show()
        }
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set("KEY_PRIORTY", selectedChip)
        Toast.makeText(activity, selectedChip, Toast.LENGTH_LONG).show()
        Log.d("KEY_PRIORITY", selectedChip)
        dismiss()
    }

}