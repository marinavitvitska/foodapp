package com.marina.admin.food;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnSignUp, btnSignIn;

    TextView txtSlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSlogin = findViewById(R.id.txtslogin);
        // Typeface face=Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        //txtSlogin.setTypeface(face);
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        startActivity(intent);
    }
}
