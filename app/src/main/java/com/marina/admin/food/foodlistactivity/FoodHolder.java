package com.marina.admin.food.foodlistactivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marina.admin.food.Model.Food;
import com.marina.admin.food.R;
import com.squareup.picasso.Picasso;

public class FoodHolder extends RecyclerView.ViewHolder {

    public interface OnFoodClickListener {
        void onClick(Food food);
    }

    public FoodHolder(@NonNull final View itemView) {
        super(itemView);
    }

    public void bind(final Food food, final OnFoodClickListener onFoodClickListener) {
        TextView tvName = itemView.findViewById(R.id.food_name);
        ImageView image = itemView.findViewById(R.id.food_image);

        tvName.setText(food.getName());

        if (food.getImage() != null && !food.getImage().isEmpty()) {
            Picasso.with(itemView.getContext())
                    .load(food.getImage())
                    .into(image);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v) {
                onFoodClickListener.onClick(food);
            }
        });
    }
}
