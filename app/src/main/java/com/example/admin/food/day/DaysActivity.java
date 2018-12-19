package com.example.admin.food.day;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.food.R;
import com.example.admin.food.day.foods.FoodByDay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaysActivity extends AppCompatActivity {

    RecyclerView rvDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        rvDays = findViewById(R.id.rvDays);
        rvDays.setLayoutManager(new LinearLayoutManager(this));

        final DaysAdapter daysAdapter = new DaysAdapter(new DaysAdapter.OnDayClickListener() {
            @Override
            public void onClick(final String day) {
                DaysActivity.this.startActivity(new Intent(DaysActivity.this, FoodByDay.class).putExtras(FoodByDay.getBundle(day)));
            }
        });

        FirebaseDatabase.getInstance().getReference("days").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                ArrayList<String> days = new ArrayList<>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    days.add(snapshot.getKey());
                }
                daysAdapter.update(days);
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });

        rvDays.setAdapter(daysAdapter);
    }
}
