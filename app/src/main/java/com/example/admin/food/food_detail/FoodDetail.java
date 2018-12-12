package com.example.admin.food.food_detail;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.food.Model.Food;
import com.example.admin.food.Model.Ingredient;
import com.example.admin.food.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodDetail extends AppCompatActivity {
    TextView food_name, food_price, food_Description;

    ImageView food_Image;

    CollapsingToolbarLayout collapsingToolbarLayout;

    FloatingActionButton btnCart;

    String foodId = "";

    FirebaseDatabase database;

    DatabaseReference foods;

    RecyclerView rvComponents;

    ComponentAdapter componentAdapter = new ComponentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        btnCart = findViewById(R.id.btnCart);

        rvComponents = findViewById(R.id.rvComponents);
        rvComponents.setLayoutManager(new LinearLayoutManager(this));
        rvComponents.setAdapter(componentAdapter);

        food_Description = findViewById(R.id.food_description);
        food_price = findViewById(R.id.food_price);
        food_name = findViewById(R.id.food_name);
        food_Image = findViewById(R.id.img_food);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        if (getIntent() != null) {
            foodId = getIntent().getStringExtra("FoodId");
        }
        if (!foodId.isEmpty()) {
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(food_Image);
                collapsingToolbarLayout.setTitle(food.getName());
                food_name.setText(food.getName());
                food_Description.setText(food.getDescription());

                List<Component> allComponents = new ArrayList<>();

                for (final Ingredient ingredient : food.Ingredients) {
                    String[] strings = ingredient.parts.split("[|]");
                    for (final String part : strings) {
                        String[] detail = part.split(":");
                        allComponents.add(new Component(detail[0], Float.valueOf(detail[1])));
                    }
                }

                List<Component> uiComponents = new ArrayList<>();

                for (final Component component : allComponents) {
                    int index = uiComponents.indexOf(component);
                    if (index != -1) {
                        uiComponents.get(index).value += component.value;
                    } else {
                        uiComponents.add(component);
                    }
                }

                componentAdapter.update(uiComponents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
