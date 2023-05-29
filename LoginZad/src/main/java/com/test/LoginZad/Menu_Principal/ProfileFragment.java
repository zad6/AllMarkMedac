package com.test.LoginZad.Menu_Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.test.LoginZad.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.TextView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

//importo dependencias y la aplicación peta cuando runeas

/*public class Perfil extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView txtNombreUsuario;
    private TextView txtCorreoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtNombreUsuario = findViewById(R.id.NombreUser);
        txtCorreoUsuario = findViewById(R.id.CorreoUser);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userID = currentUser.getUid();

        mDatabase.child("Usuarios").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String correoUsuario = dataSnapshot.child("correo").getValue(String.class);

                    txtNombreUsuario.setText(nombreUsuario);
                    txtCorreoUsuario.setText(correoUsuario);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
            }
        });
    }
}*/