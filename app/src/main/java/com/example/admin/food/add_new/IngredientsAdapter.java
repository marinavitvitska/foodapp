package com.example.admin.food.add_new;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.food.Model.Ingredient;
import com.example.admin.food.R;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter {

    public ArrayList<Ingredient> ingredientList = new ArrayList<>();

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        if (i == R.layout.item_ingredient) {
            return new ViewHolderIngredient(LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false), new ViewHolderIngredient.OnSaveIngredient() {
                @Override public void onSave(final int position, final Ingredient ingredient) {
                    ingredientList.set(position, ingredient);
                }
            });
        } else {
            return new ViewHolderAddIngredient(LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false), new View.OnClickListener() {
                @Override public void onClick(final View v) {
                    ingredientList.add(new Ingredient());
                    notifyItemInserted(ingredientList.size() - 1);
                }
            });
        }

    }

    @Override public int getItemViewType(final int position) {
        if (position < ingredientList.size()) {
            return R.layout.item_ingredient;
        } else {
            return R.layout.item_add_new;
        }
    }

    @Override public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

    }

    @Override public int getItemCount() {
        return ingredientList.size() + 1;
    }

    static class ViewHolderIngredient extends RecyclerView.ViewHolder {

        interface OnSaveIngredient {
            void onSave(int position, Ingredient ingredient);
        }

        public ViewHolderIngredient(@NonNull final View itemView, final OnSaveIngredient onSaveIngredient) {
            super(itemView);

            itemView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    String count = ((TextView) itemView.findViewById(R.id.tvCount)).getText().toString();
                    String name = ((TextView) itemView.findViewById(R.id.tvName)).getText().toString();
                    String parts = ((TextView) itemView.findViewById(R.id.tvParts)).getText().toString();
                    onSaveIngredient.onSave(getAdapterPosition(), new Ingredient(Integer.valueOf(count), name, parts));
                }
            });
        }
    }

    static class ViewHolderAddIngredient extends RecyclerView.ViewHolder {

        public ViewHolderAddIngredient(@NonNull final View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            itemView.findViewById(R.id.addNew).setOnClickListener(onClickListener);
        }
    }

}
