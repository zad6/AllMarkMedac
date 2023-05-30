package com.test.LoginZad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.test.LoginZad.Sesion.*;
import com.test.LoginZad.Menu_Principal.*;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            startActivity(new Intent(MainActivity.this, Menu_principal.class));
        }
    }

}