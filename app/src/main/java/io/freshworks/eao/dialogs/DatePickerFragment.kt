package io.freshworks.eao.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import io.freshworks.eao.Constants
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface Listener {
        fun onDateSelected(calendar: Calendar)
    }

    private var listener: Listener? = null
    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val startDate = arguments.getLong(Constants.EXTRA_START_DATE, System.currentTimeMillis())

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(activity, this, year, month, day)

        if (startDate > 0) {
            dialog.datePicker.minDate = startDate
        }
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        listener?.onDateSelected(calendar)
    }
}
