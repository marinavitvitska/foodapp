package com.example.admin.food.food_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.food.R;

public class ComponentViewHolder extends RecyclerView.ViewHolder {

    public ComponentViewHolder(@NonNull final View itemView) {
        super(itemView);
    }

    void bind(Component component) {
        itemView.<TextView>findViewById(R.id.tvName).setText(component.name);
        itemView.<TextView>findViewById(R.id.tvValue).setText(String.valueOf(component.value));
    }
}
