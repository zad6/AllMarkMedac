package com.test.LoginZad.Menu_Principal.Ventanas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.test.LoginZad.R;

public class ProductsFragment extends Fragment {

    private static final String ARG_SUPERMARKET_ID = "supermarketId";
    private static final String ARG_SUPERMARKET_NAME = "supermarketName";

    private String supermarketId;
    private String supermarketName;
    private TableLayout tableLayout;

    public static ProductsFragment newInstance(String supermarketId, String supermarketName) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUPERMARKET_ID, supermarketId);
        args.putString(ARG_SUPERMARKET_NAME, supermarketName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            supermarketId = getArguments().getString(ARG_SUPERMARKET_ID);
            supermarketName = getArguments().getString(ARG_SUPERMARKET_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        tableLayout = view.findViewById(R.id.table_layout);
        TextView tvSupermarketName = view.findViewById(R.id.text_supermarket_name);
        tvSupermarketName.setText(supermarketName);
        loadProductsFromFirestore();
        return view;
    }

    private void loadProductsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("productos")
                .whereEqualTo("id_supermercado", supermarketId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String productName = documentSnapshot.getString("nombre");
                            int productPrice = documentSnapshot.getLong("precio").intValue();
                            addProductToTable(productName, productPrice);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejo del error
                    }
                });
    }

    private void addProductToTable(String productName, int productPrice) {
        TableRow row = new TableRow(requireContext());

        TextView tvProductName = new TextView(requireContext());
        tvProductName.setText(productName);
        row.addView(tvProductName);

        TextView tvProductPrice = new TextView(requireContext());
        tvProductPrice.setText(String.valueOf(productPrice));
        row.addView(tvProductPrice);

        tableLayout.addView(row);
    }
}