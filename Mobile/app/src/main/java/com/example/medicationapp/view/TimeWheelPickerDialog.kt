
package com.example.medicationapp.view


import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.medicationapp.R
/**
 * Composable function that displays a native Android AlertDialog containing
 * NumberPickers for selecting hour and minute values.
 *
 * This dialog allows the user to pick a time by selecting an hour (0-23) and a minute (0-59).
 * The dialog uses a custom layout resource with NumberPickers and integrates it within Compose.
 *
 * @param onDismiss Callback invoked when the dialog is dismissed or cancelled.
 * @param onTimeSelected Callback invoked with the selected hour and minute when the user confirms the selection.
 */



@Composable
fun TimeWheelPickerDialog(
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    val context = LocalContext.current

    // Show Android View AlertDialog from within Compose
    AlertDialog.Builder(context).apply {
        setTitle("Select Time")

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.number_picker_layout, null, false)


        val hourPicker = dialogView.findViewById<NumberPicker>(R.id.hourPicker)
        val minutePicker = dialogView.findViewById<NumberPicker>(R.id.minutePicker)

        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        hourPicker.wrapSelectorWheel = true

        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.wrapSelectorWheel = true

        setView(dialogView)

        setPositiveButton("OK") { _, _ ->
            val selectedHour = hourPicker.value
            val selectedMinute = minutePicker.value
            onTimeSelected(selectedHour, selectedMinute)
        }

        setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            onDismiss()
        }

        create().show()
    }
}
