package com.example.softmethproject5;

import android.app.ActionBar;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import backend.Order;
import backend.Pizza;
import backend.StoreOrders;
import backend.Topping;

public class StoreOrderActivity extends AppCompatActivity {
    private StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeorders);
        Bundle args = getIntent().getBundleExtra("DATA");
        storeOrders = (StoreOrders) args.getSerializable("storeOrders");
        if(storeOrders == null) Toast.makeText(this, "StoreOrders passed incorrectly", Toast.LENGTH_SHORT).show();
        addOrderVisuals();
    }
    private void addOrderVisuals() {
        //Get storeOrderWindow as variable to add child elements
        LinearLayout layout = (LinearLayout) findViewById(R.id.storeOrderWindow);
        for(Order o: storeOrders.getStoreOrders()){
            //Make card to display order information
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setBackgroundColor(Color.WHITE);
            //TextView for phone Number
            TextView text = new TextView(this);
            text.setText(o.getCustomerPhoneNumber());
            text.setGravity(Gravity.CENTER);
            card.addView(text);
            //Scrollview for pizzas
            ScrollView scrollView = new ScrollView(this);
            LinearLayout scrollViewLayout = new LinearLayout(this);
            scrollViewLayout.setOrientation(LinearLayout.VERTICAL);
            for(Pizza p: o.getPizzaOrder()){
                TextView text3 = new TextView(this);
                text3.setText(p.getFlavor()+" with "+p.getToppings().size()+" toppings");
                text3.setGravity(Gravity.CENTER);
                scrollViewLayout.addView(text3);
            }
            scrollView.addView(scrollViewLayout);
            card.addView(scrollView);
            //TextView for price
            TextView text2 = new TextView(this);
            text2.setText(Double.toString(o.calculateTotalCost()));
            text2.setGravity(Gravity.CENTER);
            card.addView(text2);
            //Add card to storeOrderWindow
            layout.addView(card);
            //This is all formatting stuff
            ViewGroup.LayoutParams params = card.getLayoutParams();
            params.height = 150;
            params.width = ActionBar.LayoutParams.WRAP_CONTENT;
            card.setLayoutParams(params);
            card.setPadding(15,15,15,15);
            ShapeDrawable divider = new ShapeDrawable();
            divider.setIntrinsicWidth(50);
            divider.setIntrinsicHeight(50);
            divider.setAlpha(0);
            card.setDividerDrawable(divider);
            card.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            layout.setDividerDrawable(divider);
            layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        }
    }
}