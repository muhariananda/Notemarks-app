package id.muhariananda.notemarks.ui.common

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import android.text.format.DateFormat
import dagger.hilt.android.qualifiers.ActivityContext
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.Constant
import javax.inject.Inject

class DatePickerHelper @Inject constructor(
    private val fragmentManager: FragmentManager,
    @ActivityContext val context: Context
) {

    fun makeDatePicker(action: (Long) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(context.getString(R.string.text_picker_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datePicker.show(fragmentManager, Constant.DATE_PICKER)

        datePicker.apply {
            addOnPositiveButtonClickListener { dateMillis ->
                action.invoke(dateMillis)
            }
        }
    }

    fun makeTimePicker(action: (Int, Int) -> Unit) {
        val is24Format = DateFormat.is24HourFormat(context)
        val timeFormat = if (is24Format) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(timeFormat)
                .setTitleText(context.getString(R.string.text_picker_time))
                .build()
        timePicker.show(fragmentManager, Constant.TIME_PICKER)

        timePicker.apply {
            addOnPositiveButtonClickListener {
                action.invoke(hour, minute)
            }
        }
    }
}