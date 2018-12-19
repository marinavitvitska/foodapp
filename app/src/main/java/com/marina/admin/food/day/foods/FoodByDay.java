package com.marina.admin.food.day.foods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marina.admin.food.Model.Food;
import com.marina.admin.food.Model.Ingredient;
import com.marina.admin.food.R;
import com.marina.admin.food.food_detail.Component;
import com.marina.admin.food.food_detail.ComponentAdapter;
import com.marina.admin.food.foodlistactivity.FoodAdapter;
import com.marina.admin.food.foodlistactivity.FoodHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FoodByDay extends AppCompatActivity {

    ComponentAdapter componentAdapter = new ComponentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_day);

        final RecyclerView rvIngeredients = findViewById(R.id.rvIngredients);
        rvIngeredients.setLayoutManager(new LinearLayoutManager(this));

        final RecyclerView rvFoods = findViewById(R.id.rvFoods);
        rvFoods.setLayoutManager(new LinearLayoutManager(this));

        final String day = getIntent().getStringExtra(KEY_DAY);

        FirebaseDatabase.getInstance().getReference("days").child(day).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final Collection<String> messages = ((HashMap<String, String>) dataSnapshot.getValue()).values();

                FirebaseDatabase.getInstance().getReference("Foods").addValueEventListener(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        final ArrayList<Food> foods = new ArrayList<>();

                        for (final DataSnapshot child : dataSnapshot.getChildren()) {
                            final Food food = child.getValue(Food.class);

                            if (messages != null && messages.contains(food.getFoodId())) {
                                foods.add(food);
                            }
                        }

                        FoodAdapter foodAdapter = new FoodAdapter(new FoodHolder.OnFoodClickListener() {
                            @Override
                            public void onClick(final Food food) {

                            }
                        });

                        foodAdapter.update(foods);
                        rvFoods.setAdapter(foodAdapter);

                        componentAdapter.update(calculate(foods));

                        rvIngeredients.setAdapter(componentAdapter);
                    }

                    @Override public void onCancelled(@NonNull final DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {
            }
        });
    }

    List<Component> calculate(List<Food> foods) {
        List<Component> allComponents = new ArrayList<>();
        for (final Food food : foods) {


            for (final Ingredient ingredient : food.Ingredients) {
                String[] strings = ingredient.parts.split("[|]");
                for (final String part : strings) {
                    String[] detail = part.split(":");
                    allComponents.add(new Component(detail[0], Float.valueOf(detail[1])));
                }
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

        return uiComponents;
    }


    static final String KEY_DAY = "key_day";

    static public Bundle getBundle(String day) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DAY, day);
        return bundle;
    }
}
