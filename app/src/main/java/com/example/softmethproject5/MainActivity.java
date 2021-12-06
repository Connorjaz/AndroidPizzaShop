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

import java.io.Serializable;

import backend.Order;
import backend.StoreOrders;

public class MainActivity extends AppCompatActivity {
    private StoreOrders storeOrders = new StoreOrders();
    private String phoneNumber = null;
    private BroadcastReceiver receivePhoneNumber = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            phoneNumber = intent.getStringExtra("phoneNumber");
            if(phoneNumber == null) return;
        }
    };
    private BroadcastReceiver receiveOrder = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            Bundle args = intent.getBundleExtra("DATA");
            Order order = (Order)args.getSerializable("order");
            if(order == null) Toast.makeText(context, R.string.emptyOrderError, Toast.LENGTH_SHORT).show();
            else if(order.getPizzaOrder().size() > 0) storeOrders.addOrder(order);
            else Toast.makeText(context, getString(R.string.emptyOrderError), Toast.LENGTH_SHORT).show();
            phoneNumber = null;
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(receivePhoneNumber, new IntentFilter("receivePhoneNumber"));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiveOrder, new IntentFilter("receiveOrder"));
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
        Bundle args = new Bundle();
        args.putSerializable("storeOrders",(Serializable)storeOrders);
        intent.putExtra("DATA",args);
        startActivity(intent);
        releaseInstance();
    }
}