package com.test.LoginZad.Menu_Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.test.LoginZad.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.test.LoginZad.R;

import java.util.ArrayList;
import java.util.List;

public class ShopsFragment extends Fragment {

    private EditText etProductName;
    private Button btnSearch;
    private TextView tvResults;

    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);

        etProductName = view.findViewById(R.id.et_product_name);
        btnSearch = view.findViewById(R.id.btn_search);
        tvResults = view.findViewById(R.id.tv_results);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                searchProduct(productName);
            }
        });

        return view;
    }

    private void searchProduct(String productName) {
        db.collection("supermercados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> results = new ArrayList<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                String supermercado = document.getId();
                                if (document.contains("productos")) {
                                    List<String> productos = (List<String>) document.get("productos");
                                    for (String producto : productos) {
                                        if (producto.equalsIgnoreCase(productName)) {
                                            String precio = document.getString(producto);
                                            String result = "Supermercado: " + supermercado + "\nProducto: " + producto + "\nPrecio: " + precio + "\n";
                                            results.add(result);
                                        }
                                    }
                                }
                            }

                            if (results.isEmpty()) {
                                tvResults.setText("No se encontraron resultados para el producto.");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                for (String result : results) {
                                    sb.append(result);
                                }
                                tvResults.setText(sb.toString());
                            }
                        } else {
                            tvResults.setText("Error al buscar el producto.");
                        }
                    }
                });
    }
}
