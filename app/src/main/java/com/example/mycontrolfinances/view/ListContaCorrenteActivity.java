package com.example.mycontrolfinances.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.mycontrolfinances.Adapter.ContaCorrenteAdapter;
import com.example.mycontrolfinances.Entity.ContaCorrente;
import com.example.mycontrolfinances.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListContaCorrenteActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ContaCorrenteAdapter contaCorrenteAdapter;
    private LinearLayoutManager layoutManager;
    private Realm realm;
    Toolbar toolbar;

    @Override
    public void onResume() {
        super.onResume();
        if (contaCorrenteAdapter != null)
        contaCorrenteAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_conta_corrente);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.drawerLayoutCc,new Fragment()).commit();
        }

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lista de Conta Corrente");

        FloatingActionButton fab = findViewById(R.id.fabCc);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListContaCorrenteActivity.this , AddContaCorrenteActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view_contaCorrente);
        realm = Realm.getDefaultInstance();
        getAllConta();
        //realm.close();
    }
    private void getAllConta(){
        RealmResults<ContaCorrente> results = realm.where(ContaCorrente.class).findAll();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        contaCorrenteAdapter = new ContaCorrenteAdapter(this, realm, results);
        recyclerView.setAdapter(contaCorrenteAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
