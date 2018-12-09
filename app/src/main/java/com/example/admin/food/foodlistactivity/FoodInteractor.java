package com.example.admin.food.foodlistactivity;

import android.support.annotation.NonNull;

import com.example.admin.food.Model.Food;
import com.example.admin.food.Model.Ingredient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodInteractor {

    interface OnFoodLoadListener {
        void onFoodLoad(List<Food> foods);
    }

    private static final String KEY_FOODS = "Foods";

    void searchFoodsByIngridientQuery(final String query, final OnFoodLoadListener onFoodLoadListener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference foodReference = database.getReference(KEY_FOODS);

        foodReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final ArrayList<Food> foods = new ArrayList<>();

                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    final Food food = child.getValue(Food.class);

                    for (int i = 0; i < food.Ingredients.size(); i++) {
                        Ingredient ingredient = food.Ingredients.get(i);
                        List<String> strings = Arrays.asList(query.split(","));

                        if (strings.contains(ingredient.name)) {
                            foods.add(food);
                        }
                    }
                }

                onFoodLoadListener.onFoodLoad(foods);
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });
    }

}
