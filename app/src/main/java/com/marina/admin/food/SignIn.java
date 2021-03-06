package com.marina.admin.food;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marina.admin.food.Common.Common;
import com.marina.admin.food.Model.User;

public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;

    Button btnSingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtName);
        btnSingIn = (Button) findViewById(R.id.btnSignIn);

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mdialog = new ProgressDialog(SignIn.this);
                mdialog.setMessage("Please Waiting...");
                mdialog.show();

                final String name = edtPhone.getText().toString();
                final String password = edtPassword.getText().toString();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                final DatabaseReference table_user = database.getReference("User");
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() == 0) {
                            mdialog.dismiss();
                            return;
                        }
                        for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                                Intent homeIntent = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                mdialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}