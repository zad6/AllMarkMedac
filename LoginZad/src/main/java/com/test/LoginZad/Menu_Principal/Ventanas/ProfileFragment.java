package com.test.LoginZad.Menu_Principal.Ventanas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.test.LoginZad.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView nombreTextView = view.findViewById(R.id.Nombre);
        TextView correoTextView = view.findViewById(R.id.Correo);
        TextView contraseñaTextView = view.findViewById(R.id.Contraseña);

        // Obtener los valores de la base de datos
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String Nombre = currentUser.getDisplayName(); // Obtén el nombre real desde la base de datos
            String Correo = currentUser.getEmail();
            String Contraseña = "**********";

            // Establecer los valores en los elementos de la interfaz de usuario
            nombreTextView.setText(Nombre);
            correoTextView.setText(Correo);
            contraseñaTextView.setText(Contraseña);
        }

        return view;
    }


}