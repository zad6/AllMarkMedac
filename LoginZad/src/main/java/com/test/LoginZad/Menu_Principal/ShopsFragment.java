package com.test.LoginZad.Menu_Principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.test.LoginZad.R;

public class ShopsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shops, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        SearchView searchView = rootView.findViewById(R.id.searchView);

        // Configura el RecyclerView (LayoutManager, adaptador, etc.)
        // ...

        // Configura el SearchView
        // ...

        return rootView;
    }
}