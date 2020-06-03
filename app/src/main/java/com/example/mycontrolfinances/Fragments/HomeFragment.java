package com.example.mycontrolfinances.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycontrolfinances.R;
import com.example.mycontrolfinances.view.AddCartaoActivity;
import com.example.mycontrolfinances.view.AddContaPagarActivity;
import com.example.mycontrolfinances.view.AddContaReceberActivity;
import com.example.mycontrolfinances.view.ListCartaoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    View view;
    TextView textView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.txtHome);
        textView.setText("Pagina Home");

        FloatingActionButton fabPag = view.findViewById(R.id.fabCPag);
        fabPag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , AddContaPagarActivity.class));
            }
        });
        FloatingActionButton fabRec = view.findViewById(R.id.fabCReceb);
        fabRec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , AddContaReceberActivity.class));
            }
        });

        return view;
    }
}
