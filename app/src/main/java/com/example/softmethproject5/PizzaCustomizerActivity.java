package com.example.softmethproject5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import backend.Pizza;
import backend.PizzaMaker;
import backend.Size;
import backend.Topping;

public class PizzaCustomizerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzacustomizer);
        RadioGroup radioGroup = findViewById(R.id.flavorGroup);
        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if (checkedRadioButton.isChecked())
                {
                    wipeToppings();
                    ChipGroup chipGroup = findViewById(R.id.toppingGroup);
                    if(checkedRadioButton.getText().equals("Deluxe")){
                        ((ImageView)findViewById(R.id.PizzaImage)).setImageResource(R.drawable.deluxe);
                        for (int i=0; i<chipGroup.getChildCount();i++){
                            Chip chip = (Chip)chipGroup.getChildAt(i);
                            if (chip.getText().equals("Pepperoni") || chip.getText().equals("Peppers") || chip.getText().equals("Sausage") || chip.getText().equals("Onions") || chip.getText().equals("Mushrooms")) chip.setChecked(true);
                        }
                    }
                    else if(checkedRadioButton.getText().equals("Pepperoni")){
                        ((ImageView)findViewById(R.id.PizzaImage)).setImageResource(R.drawable.pepperoni);
                        for (int i=0; i<chipGroup.getChildCount();i++){
                            Chip chip = (Chip)chipGroup.getChildAt(i);
                            if (chip.getText().equals("Pepperoni")) chip.setChecked(true);
                        }
                    }
                    else{
                        ((ImageView)findViewById(R.id.PizzaImage)).setImageResource(R.drawable.hawaiian);
                        for (int i=0; i<chipGroup.getChildCount();i++){
                            Chip chip = (Chip)chipGroup.getChildAt(i);
                            if (chip.getText().equals("Ham") || chip.getText().equals("Pineapple")) chip.setChecked(true);
                        }
                    }
                }
                ((ImageView)findViewById(R.id.PizzaImage)).setVisibility(View.VISIBLE);
                calculateCosts(null);
            }
        });

        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
    }
    private void wipeToppings(){
        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            chip.setChecked(false);
        }
    }
    private Topping convertTopping(String topping){
        switch(topping.toLowerCase()){
            case "olives":
                return Topping.BLACK_OLIVES;
            case "ham":
                return Topping.HAM;
            case "mushrooms":
                return Topping.MUSHROOMS;
            case "onions":
                return Topping.ONIONS;
            case "pepperoni":
                return Topping.PEPPERONI;
            case "peppers":
                return Topping.PEPPERS;
            case "pineapple":
                return Topping.PINEAPPLE;
            case "sausage":
                return Topping.SAUSAGE;
            case "tomato":
                return Topping.TOMATO;
            case "extra cheese":
                return Topping.EXTRA_CHEESE;
            case "bacon":
                return Topping.BACON;
            default:
                return null;
        }
    }
    public void calculateCosts(View view){
        //Get Pizza type
        RadioGroup radioGroup = findViewById(R.id.flavorGroup);
        String pizzaType =  ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))) != null ? ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))).getText()+"\n": "";
        Pizza p = PizzaMaker.createPizza(pizzaType.toLowerCase());
        if(p == null) return;

        //Get size
        ChipGroup sizeGroup = findViewById(R.id.sizeGroup);
        if(sizeGroup.getCheckedChipId() != View.NO_ID) {
            String size = ((Chip) sizeGroup.findViewById(sizeGroup.getCheckedChipId())).getText().toString();
            if (size.toLowerCase().equals("medium")) p.changeSize(Size.MEDIUM);
            else if (size.toLowerCase().equals("large")) p.changeSize(Size.LARGE);
            else p.changeSize(Size.SMALL);
        }
        //Get toppings
        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            Topping t = convertTopping(chip.getText().toString());
            if(t == null || p.getToppings().contains(t)) continue;
            p.addToppings(t);
        }
        ((TextView)findViewById(R.id.Total)).setText("Subtotal: $"+p.price());
    }
    public void submitPizza(View view){
        //Get Pizza type
        RadioGroup radioGroup = findViewById(R.id.flavorGroup);
        String pizzaType =  ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))) != null ? ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))).getText()+"\n": "";
        if(pizzaType == null){
            Toast.makeText(this, "Please select a Pizza Type", Toast.LENGTH_SHORT).show();
            return;
        }
        Pizza p = PizzaMaker.createPizza(pizzaType.toLowerCase());
        if(p == null) return;

        //Get size
        ChipGroup sizeGroup = findViewById(R.id.sizeGroup);
        if(sizeGroup.getCheckedChipId() != View.NO_ID) {
            String size = ((Chip) sizeGroup.findViewById(sizeGroup.getCheckedChipId())).getText().toString();
            if (size.toLowerCase().equals("medium")) p.changeSize(Size.MEDIUM);
            else if (size.toLowerCase().equals("large")) p.changeSize(Size.LARGE);
            else p.changeSize(Size.SMALL);
        }
        //Get toppings
        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            Topping t = convertTopping(chip.getText().toString());
            if(t == null || p.getToppings().contains(t)) continue;
            p.addToppings(t);
        }

        //send pizza
        Intent intent = new Intent("receivePizza");
        Bundle args = new Bundle();
        args.putSerializable("pizza",(Serializable)p);
        intent.putExtra("DATA",args);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}