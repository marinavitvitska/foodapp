package com.marina.admin.food.foodlistactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.marina.admin.food.Model.Food;
import com.marina.admin.food.R;
import com.marina.admin.food.food_detail.FoodDetail;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity implements FoodHolder.OnFoodClickListener {
    FirebaseDatabase database;

    DatabaseReference foodList;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    String categoryId = "";

    FoodAdapter adapter;

    FoodAdapter searchadapter = new FoodAdapter(this);

    List<String> suggestList = new ArrayList<>();

    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            loadListFood();
        }

        materialSearchBar = findViewById(R.id.searshBar);
        materialSearchBar.setHint("Пошук");

        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    @Override public void onClick(final Food food) {
        Intent foodDetail = new Intent(this, FoodDetail.class);
        foodDetail.putExtra("FoodId", food.getFoodId());
        startActivity(foodDetail);
    }

    private void startSearch(CharSequence text) {
        FoodInteractor foodInteractor = new FoodInteractor();
        foodInteractor.searchFoodsByIngridientQuery(text.toString(), new FoodInteractor.OnFoodLoadListener() {
            @Override public void onFoodLoad(final List<Food> foods) {
                searchadapter.update(foods);
            }
        });

        recyclerView.setAdapter(searchadapter);
    }

    private void loadSuggest() {
        foodList.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void loadListFood() {
        FirebaseDatabase.getInstance().getReference("Foods").addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final ArrayList<Food> foods = new ArrayList<>();

                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    final Food food = child.getValue(Food.class);

                    if (food.getMenuId().equals(categoryId)) {
                        foods.add(food);
                    }
                }

                adapter = new FoodAdapter(new FoodHolder.OnFoodClickListener() {
                    @Override public void onClick(final Food food) {
                        callFood(food);
                    }
                });

                adapter.update(foods);

                recyclerView.setAdapter(adapter);
            }

            @Override public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });
    }

    private void callFood(Food food) {
        Intent foodDetail = new Intent(FoodListActivity.this, FoodDetail.class);
        foodDetail.putExtra("FoodId", food.getFoodId());
        startActivity(foodDetail);
    }
}
