package com.example.softmethproject5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void new_order(View view) {
        Intent intent = new Intent(this, NewOrderActivity.class);
        startActivity(intent);
    }
    public void current_order(View view) {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void store_orders(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        startActivity(intent);
    }
}