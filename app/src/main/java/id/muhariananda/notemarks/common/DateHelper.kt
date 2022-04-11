package id.muhariananda.notemarks.common

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    companion object {
        fun getCurrentDate(): String {
            val current = Date()
            val formatter = SimpleDateFormat("EEE, k:mm", Locale.getDefault())
            return formatter.format(current)
        }
    }

}