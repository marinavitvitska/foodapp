package com.marina.admin.food.day;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marina.admin.food.R;

import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayViewHolder> {

    interface OnDayClickListener {
        void onClick(String day, Boolean isChecked);
    }

    public DaysAdapter(final OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    private OnDayClickListener onDayClickListener;

    private ArrayList<String> days = new ArrayList<>();

    public void update(ArrayList<String> days) {
        this.days = days;
        notifyDataSetChanged();
    }

    @NonNull @Override public DayViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new DayViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_day, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull final DayViewHolder dayViewHolder, final int i) {
        dayViewHolder.bind(days.get(i), onDayClickListener);
    }

    @Override public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {

        public DayViewHolder(@NonNull final View itemView) {
            super(itemView);
        }

        public void bind(final String day, final OnDayClickListener onDayClickListener) {
            ((TextView) itemView.findViewById(R.id.tvDay)).setText(day);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(final View v) {
                    CheckBox checkBox = itemView.findViewById(R.id.checkbox);
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }
                    onDayClickListener.onClick(day, checkBox.isChecked());
                }
            });
        }
    }

}
