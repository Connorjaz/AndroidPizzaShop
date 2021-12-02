package com.example.softmethproject5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String phoneNumber = null;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            phoneNumber = intent.getStringExtra("phoneNumber");
            if(phoneNumber == null) return;
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("receivePhoneNumber"));
    }

    public void new_order(View view) {
        Intent intent = new Intent(this, NewOrderActivity.class);
        startActivity(intent);
        releaseInstance();
    }
    public void current_order(View view) {
        if(phoneNumber == null) return;
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void store_orders(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        startActivity(intent);
        releaseInstance();
    }
}