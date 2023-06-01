package com.test.LoginZad.Menu_Principal.Ventanas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;
    private static Map<String, Integer> supermarketImages;

    private static class Shop {
        String id;
        String nombre;
        String fotoUrl;

        Shop(String id, String nombre, String fotoUrl) {
            this.id = id;
            this.nombre = nombre;
            this.fotoUrl = fotoUrl;
        }
    }

    private static class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        ImageView ivFoto;

        ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.text_nombre);
            ivFoto = itemView.findViewById(R.id.image_foto);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        shopAdapter = new ShopAdapter();
        recyclerView.setAdapter(shopAdapter);
        initializeSupermarketImages();
        loadShopsFromFirestore();
        return view;
    }

    private void initializeSupermarketImages() {
        supermarketImages = new HashMap<>();
        supermarketImages.put("Mercadona", R.drawable.mercadona);
        supermarketImages.put("Carrefour", R.drawable.carrefour);
        supermarketImages.put("Eroski", R.drawable.eroski);
        supermarketImages.put("Dia", R.drawable.dia);
        supermarketImages.put("Lidl", R.drawable.lidl);
        supermarketImages.put("Alcampo", R.drawable.alcampo);
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
                            String id = documentSnapshot.getId();
                            String nombre = documentSnapshot.getString("nombre");
                            String fotoUrl = documentSnapshot.getString("fotoUrl");
                            shopList.add(new Shop(id, nombre, fotoUrl));
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

            // Obtener el identificador de la imagen del supermercado
            Integer imagenId = supermarketImages.get(shop.nombre);

            // Configurar la imagen en el ImageView
            if (imagenId != null) {
                holder.ivFoto.setImageResource(imagenId);
            } else {
                holder.ivFoto.setImageResource(R.drawable.placeholder_image);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductsFragment productsFragment = ProductsFragment.newInstance(shop.id, shop.nombre);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, productsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return shopList.size();
        }
    }
}