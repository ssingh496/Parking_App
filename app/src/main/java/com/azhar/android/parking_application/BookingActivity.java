
package com.azhar.android.parking_application;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.format.DateFormat;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity
{

    TextView cardRegBook;
    TextView parkingSlotNumberBook;
    Button bookButton;
    static EditText DateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        cardRegBook = (TextView) findViewById(R.id.cardRegBook);
        parkingSlotNumberBook = (TextView) findViewById(R.id.parkingSlotNumberBook);
        bookButton = (Button) findViewById(R.id.bookButton);
        DateEdit = (EditText) findViewById(R.id.timeBook);

        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
                showDatePickerDialog(v);

            }
        });

        bookButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (cardRegBook.getText().toString().isEmpty() || parkingSlotNumberBook.getText().toString().isEmpty() || DateEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (Integer.parseInt(parkingSlotNumberBook.getText().toString()) > 15)
                {
                        Toast.makeText(getApplicationContext(), "Choose from the available Parking slot 1-15", Toast.LENGTH_LONG).show();
                        return;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Booking is confirmed", Toast.LENGTH_LONG).show();
                }
                finish();




            }

        });



    }


    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateEdit.setText(DateEdit.getText() + " -" + hourOfDay + ":" + minute);
        }

    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            DateEdit.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
