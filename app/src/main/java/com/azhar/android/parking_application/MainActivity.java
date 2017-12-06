package com.azhar.android.parking_application;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    TextView cardReg;
    TextView uniquePin;
    TextView timeIn;
    TextView timeOut;
    TextView cost;
    TextView slot;
    Button confirm;
    TextView message;
    List<String> spots =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardReg=(TextView)findViewById(R.id.cardReg);
        uniquePin=(TextView)findViewById(R.id.uniquePin);
        timeIn=(TextView)findViewById(R.id.timeIn);
        timeOut=(TextView)findViewById(R.id.timeOut);
        cost=(TextView)findViewById(R.id.cost);
        slot=(TextView)findViewById(R.id.slot);
        message=(TextView)findViewById(R.id.message);
        confirm=( Button)findViewById(R.id.confirm);


        confirm.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        if(cardReg.getText().toString().isEmpty()||uniquePin.getText().toString().isEmpty()||timeIn.getText().toString().isEmpty()||timeOut.getText().toString().isEmpty()
                                ||cost.getText().toString().isEmpty()||slot.getText().toString().isEmpty())
                        {
                            message.setTextColor(Color.RED);
                            message.setText("Fill  in Everything ");

                        }
                        else {
                            if(spots.contains(slot.getText().toString()))
                            {
                                message.setTextColor(Color.RED);
                                message.setText("Slot Already taken");
                            }else
                            {
                                spots.add(slot.getText().toString());
                                message.setTextColor(Color.GREEN);
                                message.setText("Parking Confirmed :) ");
                            }

                            cost.setText(Double.toString(calculateTotal()));

                        }

                    }

        });



    }

    private double calculateTotal(){
        double outTime= Double.parseDouble(timeOut.getText().toString());
        double inTime= Double.parseDouble(timeIn.getText().toString());
        double parkingFee =(outTime-inTime)*50;
        return parkingFee;
    }
}
