package com.example.softmethproject5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NewOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);
    }
    public void current_order(View view) {
        EditText text = (EditText)findViewById(R.id.editTextPhone);
        String phoneNumber = text.getText().toString();
        if(!(phoneNumber.matches("[0-9]+") && phoneNumber.length() == 10)) {
            Toast.makeText(this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent("receivePhoneNumber");
        intent.putExtra("phoneNumber", phoneNumber);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}