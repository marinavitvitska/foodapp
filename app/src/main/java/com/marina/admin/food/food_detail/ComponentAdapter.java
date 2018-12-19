package com.marina.admin.food.food_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marina.admin.food.R;

import java.util.ArrayList;
import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder> {

    private List<Component> componentList = new ArrayList<>();

    public void update(List<Component> componentList) {
        this.componentList = componentList;
        notifyDataSetChanged();
    }

    @NonNull @Override public ComponentViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new ComponentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_componente, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull final ComponentViewHolder componentViewHolder, final int i) {
        componentViewHolder.bind(componentList.get(i));
    }

    @Override public int getItemCount() {
        return componentList.size();
    }
}
