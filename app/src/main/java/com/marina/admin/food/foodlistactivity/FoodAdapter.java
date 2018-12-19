package com.marina.admin.food.foodlistactivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marina.admin.food.Model.Food;
import com.marina.admin.food.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodHolder> {

    private FoodHolder.OnFoodClickListener onFoodClickListener;

    public FoodAdapter(final FoodHolder.OnFoodClickListener onFoodClickListener) {
        this.onFoodClickListener = onFoodClickListener;
    }

    public List<Food> foods = new ArrayList<>();

    public void update(List<Food> food) {
        this.foods = food;
        notifyDataSetChanged();
    }

    public void add(List<Food> food) {
        this.foods.addAll(food);
        notifyDataSetChanged();
    }

    @NonNull @Override public FoodHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new FoodHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull final FoodHolder foodHolder, final int position) {
        foodHolder.bind(foods.get(position), onFoodClickListener);
    }

    @Override public int getItemCount() {
        return foods.size();
    }
}
