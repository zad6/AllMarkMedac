package com.test.LoginZad.Sesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.LoginZad.R;


public class DashBoard extends AppCompatActivity {


    private Button btnSalir;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        btnSalir = findViewById(R.id.btnSalir);

        mAuth = FirebaseAuth.getInstance();

        btnSalir.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));

        });

    }//End onCreate



}