package com.marina.admin.food;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marina.admin.food.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone, edtName, edtPassword;

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtPhone = findViewById(R.id.edtPhone);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mdialog = new ProgressDialog(SignUp.this);
                mdialog.setMessage("Please Waiting...");
                mdialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtName.getText().toString()).exists()) {
                            mdialog.dismiss();
                        } else {
                            mdialog.dismiss();
                            User user = new User(edtName.getText().toString(), edtPassword.getText().toString(), edtPhone.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            finish();
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
