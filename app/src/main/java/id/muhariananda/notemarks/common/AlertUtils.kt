package id.muhariananda.notemarks.common

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import id.muhariananda.notemarks.R

class AlertUtils {
    companion object {
        fun makeAlertToDelete(context: Context, title: String, onConfirmation: () -> Unit) {
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

        fun makeToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}