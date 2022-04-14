package id.muhariananda.notemarks.ui.common

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ActivityContext
import id.muhariananda.notemarks.R
import javax.inject.Inject

class AlertHelper @Inject constructor(
    @ActivityContext val context: Context
) {
    fun makeAlertToDelete(title: String, onConfirmation: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle(title)
            setMessage(context.getString(R.string.text_message_delete))
            setNegativeButton(context.getString(R.string.text_no)) { _, _ -> }
            setPositiveButton(context.getString(R.string.text_yes)) { _, _ ->
                onConfirmation.invoke()
            }
        }.show()
    }

    fun makeUndoSnackBar(view: View, title: String, action: () -> Unit) {
        Snackbar.make(
            view,
            context.getString(R.string.text_delete_item, title),
            Snackbar.LENGTH_LONG
        )
            .setAction(context.getString(R.string.text_undo)) {
                action.invoke()
            }
            .show()
    }
}