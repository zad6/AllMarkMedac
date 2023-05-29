package com.test.LoginZad.Sesion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.test.LoginZad.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtUser;            // Campo de texto para el nombre de usuario
    private EditText txtMail;            // Campo de texto para el correo electrónico
    private EditText txtPhone;           // Campo de texto para el número de teléfono
    private TextInputLayout txtPassword; // Campo de texto para la contraseña
    private Button btnRegister;          // Botón de registro
    private TextView lblLogin;           // Etiqueta para iniciar sesión

    private String userID;               // ID del usuario
    private FirebaseAuth mAuth;          // Objeto de autenticación de Firebase
    private FirebaseFirestore db;       // Objeto de base de datos de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Asignación de los elementos de la interfaz a las variables correspondientes
        txtUser = findViewById(R.id.txtUser);
        txtMail = findViewById(R.id.txtMail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        lblLogin = findViewById(R.id.lblLogin);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();             // Inicialización del objeto de autenticación
        db = FirebaseFirestore.getInstance();           // Inicialización del objeto de base de datos

        // Acción al hacer clic en el botón de registro
        btnRegister.setOnClickListener(view -> {
            createuser();
        });

        // Acción al hacer clic en la etiqueta de inicio de sesión
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    // Método para abrir la actividad de inicio de sesión
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Método para crear un usuario
    public void createuser() {
        String name = txtUser.getText().toString();
        String mail = txtMail.getText().toString();
        String phone = txtPhone.getText().toString();
        String password = txtPassword.getEditText().getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            // Validación de campos vacíos y muestra de errores en caso necesario
            if (TextUtils.isEmpty(name)) {
                txtUser.setError("Ingrese un Nombre");
                txtUser.requestFocus();
            } else if (TextUtils.isEmpty(mail)) {
                txtMail.setError("Ingrese un Correo");
                txtMail.requestFocus();
            } else if (TextUtils.isEmpty(phone)) {
                txtPhone.setError("Ingrese un Teléfono");
                txtPhone.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                txtPassword.setError("Ingrese una Contraseña");
                txtPassword.requestFocus();
            }
        } else {
            // Creación de usuario con el correo y contraseña proporcionados
            mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("usuarios").document(userID);

                        // Creación de un mapa con los datos del usuario
                        Map<String, Object> user = new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Correo", mail);
                        user.put("Teléfono", phone);
                        user.put("Contraseña", password);
                        user.put("Cartera", 0);

                        // Guardado de los datos del usuario en la base de datos
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados" + userID);
                            }
                        });

                        Toast.makeText(RegisterActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}