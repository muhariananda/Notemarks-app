package id.muhariananda.notemarks.common

import android.util.Log
import id.muhariananda.notemarks.common.Constant.DATE_LONG_FORMAT
import id.muhariananda.notemarks.common.Constant.DATE_MEDIUM_FORMAT
import id.muhariananda.notemarks.common.Constant.DATE_SIMPLE_FORMAT
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateHelper {

    companion object {
        fun getCurrentDate(): String {
            val current = Date()
            val formatter = SimpleDateFormat(DATE_SIMPLE_FORMAT, Locale.getDefault())
            return formatter.format(current)
        }

        fun convertMillisToDate(dateMillis: Long): String {
            val dateFormatter = SimpleDateFormat(DATE_MEDIUM_FORMAT, Locale.getDefault())
            return dateFormatter.format(Date(dateMillis))
        }

        fun getDateReminderFromMillis(dateMillis: Long, hour: Int, minute: Int): Long {
            val dateFromMillis = convertMillisToDate(dateMillis)
            val dateTimeFormatter =
                DateTimeFormatter.ofPattern(DATE_LONG_FORMAT, Locale.getDefault())
            val date = LocalDateTime.parse("$dateFromMillis $hour:$minute", dateTimeFormatter)

            val today = Calendar.getInstance()
            val picker = Calendar.getInstance().also { cal ->
                date.apply {
                    cal.set(year, (monthValue - 1), dayOfMonth, hour, minute)
                }
            }
            return (picker.timeInMillis / 1000L) - (today.timeInMillis / 1000L)
        }
    }

}