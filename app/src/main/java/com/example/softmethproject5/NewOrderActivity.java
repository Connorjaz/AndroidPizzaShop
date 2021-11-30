package com.example.softmethproject5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);
    }
    public void current_order(View view) {
        EditText text = (EditText)findViewById(R.id.editTextPhone);
        String phoneNumber = text.getText().toString();
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
        finish();
    }
}