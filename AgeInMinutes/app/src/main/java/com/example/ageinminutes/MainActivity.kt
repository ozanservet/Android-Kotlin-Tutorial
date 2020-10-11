package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker.setOnClickListener {view ->
            clickDatePicker(view)
        }
    }

    fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener {
                        view, selectedYear, selectedMonth, selectedDayOfMonth ->
                    Toast.makeText(this, "The chosen year is $selectedYear the month is $selectedMonth and the day is $selectedDayOfMonth ", Toast.LENGTH_LONG).show()

                    val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"     // +1 because first month starts from 0

                    tvSelectedDate.setText(selectedDate)

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    val theDate = sdf.parse(selectedDate) 
                    val selectedDateInMinutes = theDate!!.time / 60000      // milisecond to minute conversion
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))     // getting the current date
                    val currentDateToMinutes = currentDate!!.time / 60000       // milisecond to minute conversion
                    val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes

                    val differencesInDays = (differenceInMinutes / 1440).toInt()      // differences in days
                    tvSelectedDateInMinutes.setText(differencesInDays.toString())       // print days
                }
                ,year
                ,month
                ,day)

                dpd.datePicker.setMaxDate(Date().time - 86400000)		// future dates are not allowed for selection.
                dpd.show()
    }
}