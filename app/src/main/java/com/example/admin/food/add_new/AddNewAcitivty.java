package com.example.admin.food.add_new;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.food.Model.Food;
import com.example.admin.food.Model.Ingredient;
import com.example.admin.food.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class AddNewAcitivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_acitivty);

        final EditText tvName = findViewById(R.id.tvName);
        final EditText tvImage = findViewById(R.id.tvImage);
        final EditText tvDescription = findViewById(R.id.tvDescription);
        final EditText tvDiscount = findViewById(R.id.tvDiscount);

        Button bSave = findViewById(R.id.bSave);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String id = UUID.randomUUID().toString();

                Food food = new Food(tvName.getText().toString(),
                        tvImage.getText().toString(),
                        tvDescription.getText().toString(),
                        tvDiscount.getText().toString(),
                        "0", id, new ArrayList<Ingredient>());


                FirebaseDatabase.getInstance().getReference("Foods").child(id).setValue(food);
            }
        });
    }
}
