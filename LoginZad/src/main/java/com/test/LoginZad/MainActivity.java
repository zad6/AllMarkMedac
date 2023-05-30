package com.test.LoginZad;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/*import android.content.Intent;


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
}*/

import android.content.res.AssetManager;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Leer el archivo JSON desde la carpeta "assets"
        String json = readJSONFromAssets("productos.json");

        // Aquí puedes utilizar el contenido del JSON para subir los datos a Firestore
        try {
            // Obtener la instancia de Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Parsear la cadena JSON a una lista de objetos
            List<Producto> productos = parseJSONToProductos(json);

            // Recorrer la lista de productos y subirlos a Firestore
            for (Producto producto : productos) {
                // Obtener una referencia al documento en la colección "productos" con el ID del producto
                DocumentReference productoRef = db.collection("productos").document(producto.getId());

                // Crear un mapa de valores para el producto
                Map<String, Object> productoData = new HashMap<>();
                productoData.put("id_supermercado", producto.getIdSupermercado());
                productoData.put("nombre", producto.getNombre());
                productoData.put("precio", producto.getPrecio());

                // Subir los datos del producto a Firestore
                productoRef.set(productoData)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Producto subido correctamente"))
                        .addOnFailureListener(e -> Log.e(TAG, "Error al subir el producto", e));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error al parsear el JSON", e);
        }
    }

    private String readJSONFromAssets(String fileName) {
        String json;
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        }
        return json;
    }

    // Método para parsear la cadena JSON a una lista de objetos Producto
    private List<Producto> parseJSONToProductos(String json) throws JSONException {
        List<Producto> productos = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String idSupermercado = jsonObject.getString("id_supermercado");
            String nombre = jsonObject.getString("nombre");
            int precio = jsonObject.getInt("precio");
            Producto producto = new Producto(id, idSupermercado, nombre, precio);
            productos.add(producto);
        }
        return productos;
    }
}