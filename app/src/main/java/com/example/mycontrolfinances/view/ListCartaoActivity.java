package com.example.mycontrolfinances.view;

import android.content.Intent;
import android.os.Bundle;
import com.example.mycontrolfinances.Adapter.CartaoAdapter;
import com.example.mycontrolfinances.Entity.CartaoCredito;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.example.mycontrolfinances.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListCartaoActivity extends AppCompatActivity{


    private RecyclerView recyclerView;
    private CartaoAdapter cartaoAdapter;
    private LinearLayoutManager layoutManager;
    private Realm realm;

    Toolbar toolbar;

    @Override
    public void onResume() {
        super.onResume();
        if (cartaoAdapter != null)
            cartaoAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cartao);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.drawerLayoutCartao,new Fragment()).commit();
        }

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lista de Cartões");

        FloatingActionButton fab = findViewById(R.id.fabCartao);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCartaoActivity.this , AddCartaoActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view_cartao);
 /*      Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("ControlFinances.realm").build();
        Realm.setDefaultConfiguration(configuration);*/

        realm = Realm.getDefaultInstance();
        getAllCartao();
    }
    private void getAllCartao(){
        RealmResults<CartaoCredito> results = realm.where(CartaoCredito.class).findAll();
        layoutManager = new LinearLayoutManager(this);
        // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setHasFixedSize(true);
        cartaoAdapter = new CartaoAdapter(this, realm, results);
        recyclerView.setAdapter(cartaoAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
