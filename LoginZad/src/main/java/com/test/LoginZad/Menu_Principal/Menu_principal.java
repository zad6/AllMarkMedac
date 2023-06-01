package com.test.LoginZad.Menu_Principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.test.LoginZad.Menu_Principal.Ventanas.AboutFragment;
import com.test.LoginZad.Menu_Principal.Ventanas.ProfileFragment;
import com.test.LoginZad.Menu_Principal.Ventanas.ShopsFragment;
import com.test.LoginZad.Menu_Principal.Ventanas.WalletFragment;
import com.test.LoginZad.R;
import com.test.LoginZad.Sesion.*;

public class Menu_principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_wallet);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_wallet:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletFragment()).commit();
                break;

            case R.id.nav_shops:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShopsFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "Sesión cerrada!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getUserData() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference usuarioActualRef = FirebaseFirestore.getInstance().collection("usuarios").document(userID);

        usuarioActualRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Long cartera = documentSnapshot.getLong("Cartera");

                // Mostrar el valor de la cartera en un componente de tu elección (por ejemplo, un Toast)
                Toast.makeText(Menu_principal.this, "Valor de la cartera: " + cartera, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            // Manejar el error en caso de que no se pueda obtener el valor de la cartera
            Toast.makeText(Menu_principal.this, "Error al obtener el valor de la cartera", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}