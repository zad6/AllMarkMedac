package com.test.LoginZad.Menu_Principal.Ventanas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.test.LoginZad.R;
import java.util.ArrayList;
import java.util.List;

public class ShopsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;

    private static class Shop {
        String nombre;

        Shop(String nombre) {
            this.nombre = nombre;
        }
    }

    private static class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;

        ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.text_nombre);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        shopAdapter = new ShopAdapter();
        recyclerView.setAdapter(shopAdapter);
        loadShopsFromFirestore();
        return view;
    }

    private void loadShopsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("supermercados")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Shop> shopList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String nombre = documentSnapshot.getString("nombre");
                            shopList.add(new Shop(nombre));
                        }
                        shopAdapter.setShopList(shopList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejo del error
                    }
                });
    }

    private class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

        private List<Shop> shopList = new ArrayList<>();

        void setShopList(List<Shop> shopList) {
            this.shopList = shopList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
            return new ShopViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
            Shop shop = shopList.get(position);
            holder.tvNombre.setText(shop.nombre);
        }

        @Override
        public int getItemCount() {
            return shopList.size();
        }
    }
}
