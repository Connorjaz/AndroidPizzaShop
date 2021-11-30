package com.example.softmethproject5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentorder);
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phoneNumber");
        TextView text = (TextView) findViewById(R.id.textView);
        if(phoneNumber == null) return;
        text.setText("Welcome "+phoneNumber);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}