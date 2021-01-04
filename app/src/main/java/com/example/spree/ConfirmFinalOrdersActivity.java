package com.example.spree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmFinalOrdersActivity extends AppCompatActivity {

    private EditText shipName, shipPhone, shipCity, shipAdd;
    private Button confirmShip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_orders);

        confirmShip = findViewById(R.id.confirmShipment);
        shipName = findViewById(R.id.etxt1);
        shipPhone = findViewById(R.id.etxt2);
        shipAdd = findViewById(R.id.etxt3);
        shipCity = findViewById(R.id.etxt4);

    }
}