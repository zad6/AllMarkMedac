package com.test.LoginZad.Menu_Principal.Ventanas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.test.LoginZad.Menu_Principal.Menu_principal;
import com.test.LoginZad.R;

public class WalletFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        Button walletButton = view.findViewById(R.id.wallet_button);
        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu_principal menuPrincipal = (Menu_principal) getActivity();
                if (menuPrincipal != null) {
                    menuPrincipal.getUserData();
                }
            }
        });

        return view;
    }
}