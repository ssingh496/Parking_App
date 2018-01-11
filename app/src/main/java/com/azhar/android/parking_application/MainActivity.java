package com.azhar.android.parking_application;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.widget.DigitalClock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.name;
import static com.azhar.android.parking_application.R.id.bookingEvent;
import static com.azhar.android.parking_application.R.id.email;

public class MainActivity extends AppCompatActivity {

    TextView cardReg;
    TextView parkingSlotNumber;
    TextView timeIn;
    TextView timeOut;
    TextView cost;
    TextView slot;
    Button confirm;
    Button bookingEvent;
    TextView message;
    List<String> spots = new ArrayList<>();
    TextClock simpleDigitalClock;
    TextView notificationBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AWSMobileClient.getInstance().initialize(this).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardReg = (TextView) findViewById(R.id.cardReg);
        parkingSlotNumber = (TextView) findViewById(R.id.parkingSlotNumber);
        timeIn = (TextView) findViewById(R.id.timeIn);
        timeOut = (TextView) findViewById(R.id.timeOut);
        cost = (TextView) findViewById(R.id.cost);
        slot = (TextView) findViewById(R.id.slot);
        confirm = (Button) findViewById(R.id.confirm);
        bookingEvent = (Button) findViewById(R.id.bookingEvent);
        simpleDigitalClock = (TextClock) findViewById(R.id.digitalClock);
        notificationBooking = (TextView) findViewById(R.id.notificationBooking);
        notificationBooking.setVisibility(View.INVISIBLE);
        simpleDigitalClock.setVisibility(View.INVISIBLE);

        confirm.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                notificationBooking.setVisibility(View.INVISIBLE);
                simpleDigitalClock.setVisibility(View.INVISIBLE);
                if (cardReg.getText().toString().isEmpty() || parkingSlotNumber.getText().toString().isEmpty() || timeIn.getText().toString().isEmpty() || timeOut.getText().toString().isEmpty()
                        || slot.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT).show();
                    notificationBooking.setVisibility(View.INVISIBLE);
                    simpleDigitalClock.setVisibility(View.INVISIBLE);

                } else {

                    if (Integer.parseInt(parkingSlotNumber.getText().toString()) > 15) {
                        notificationBooking.setVisibility(View.INVISIBLE);
                        simpleDigitalClock.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Choose from the available Parking slot 1-15", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ((slot.getText().toString().equalsIgnoreCase("Yes"))) {
                        System.out.println("first loop");
                        Toast.makeText(getApplicationContext(), "Car Is Parked in this slot and the timer is running", Toast.LENGTH_LONG).show();

                    } else if ((slot.getText().toString().equalsIgnoreCase("No"))) {
                        notificationBooking.setVisibility(View.INVISIBLE);
                        simpleDigitalClock.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Parking is available for this slot", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        notificationBooking.setVisibility(View.INVISIBLE);
                        simpleDigitalClock.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), " Please enter Yes or NO in slot ", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (spots.contains(parkingSlotNumber.getText().toString())) {
                        notificationBooking.setVisibility(View.INVISIBLE);
                        simpleDigitalClock.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Please choose from the other available slots", Toast.LENGTH_LONG).show();
                    } else {
                        spots.add(parkingSlotNumber.getText().toString());
                        Toast.makeText(getApplicationContext(), "Parking Confirmed for this slot", Toast.LENGTH_LONG).show();
                        notificationBooking.setText(" Timmer is started");
                        notificationBooking.setVisibility(View.VISIBLE);
                        simpleDigitalClock.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notificationBooking.setVisibility(View.VISIBLE);
                            }
                        }, 10000);
                    }

                    cost.setText(Double.toString(calculateTotal()));

                }

                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://127.0.0.1")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                ApiService service = retrofit.create(ApiService.class);

                ParkingUser parkingUser = new ParkingUser();
                parkingUser.setName(cardReg.getText().toString());
                parkingUser.setEmail("xyz@gmail.com");
                parkingUser.setPassword("test");
                Call<ParkingUser> call = service.insertData(parkingUser.getName(), parkingUser.getEmail(), parkingUser.getPassword());

                call.enqueue(new Callback<ParkingUser>() {
                    @Override
                    public void onResponse(Call<ParkingUser> call, Response<ParkingUser> response) {

                        Toast.makeText(MainActivity.this, "response" + response, Toast.LENGTH_SHORT).show();
                        cardReg.setText("");
                    }

                    @Override
                    public void onFailure(Call<ParkingUser> call, Throwable t) {

                        Log.i("Hello", "" + t);
                        Toast.makeText(MainActivity.this, "Throwable" + t, Toast.LENGTH_SHORT).show();

                    }
                });


            }

        });


        bookingEvent.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent bookingActivity = new Intent(MainActivity.this, BookingActivity.class);
                startActivity(bookingActivity);
                //finish();

            }


        });


    }


    private double calculateTotal() {
        double outTime = Double.parseDouble(timeOut.getText().toString());
        double inTime = Double.parseDouble(timeIn.getText().toString());
        double parkingFee = (outTime - inTime) * 50;
        return parkingFee;
    }
}
