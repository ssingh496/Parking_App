package com.azhar.android.parking_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent homeIntent = new Intent(this, LoginActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
