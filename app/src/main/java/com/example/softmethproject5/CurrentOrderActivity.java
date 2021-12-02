package com.example.softmethproject5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CurrentOrderActivity extends AppCompatActivity {
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            finish();
        }
    };
    public void finish(){
        super.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_currentorder);
        String phoneNumber = intent.getStringExtra("phoneNumber");
        Toast.makeText(this, "New order activity has been created", Toast.LENGTH_SHORT).show();
        TextView text = (TextView) findViewById(R.id.welcomeMessage);
        text.setText("Welcome "+phoneNumber);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("receivePhoneNumber"));
    }
    @Override protected void onResume(){
        super.onResume();
        Toast.makeText(this, "New order activity has been resumed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void addPizza(View view) {
        LinearLayout layout = (LinearLayout) view.getParent();
        for(int i = 0; i < 4; i++) {
            CardView card = new CardView(this);
            layout.addView(card);
        }
    }
}