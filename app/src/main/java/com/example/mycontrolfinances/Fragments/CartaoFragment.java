package com.example.mycontrolfinances.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycontrolfinances.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartaoFragment extends Fragment{

    View view;
    TextView textView;
    public CartaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cartao, container, false);

        textView = view.findViewById(R.id.txtCartao);
        textView.setText("Pagina Cartao");
        return view;
    }
}
