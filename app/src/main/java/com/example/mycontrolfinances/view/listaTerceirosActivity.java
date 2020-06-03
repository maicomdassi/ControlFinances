package com.example.mycontrolfinances.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.mycontrolfinances.Adapter.TerceiroAdapter;
import com.example.mycontrolfinances.Entity.Terceiro;
import com.example.mycontrolfinances.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class listaTerceirosActivity extends AppCompatActivity {

    private View view;
    private RecyclerView recyclerView;
    TextView textView;
    private TerceiroAdapter terceiroAdapter;

    private LinearLayoutManager layoutManager;
    private Realm realm;

    Toolbar toolbar;
    DrawerLayout mDrawerLayout ;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onResume() {
        super.onResume();
        if (terceiroAdapter != null)
            terceiroAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_terceiros);

 /*       Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.drawerLayoutTerceiros,new Fragment()).commit();
        }

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lista de Terceiros");
        //toolbar.setTitleTextColor(123);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(listaTerceirosActivity.this , AddTerceiroActivity.class));

/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        recyclerView = findViewById(R.id.recycler_view_terceiros);
/*        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("ControlFinances.realm").build();
        Realm.setDefaultConfiguration(configuration);*/

        realm = Realm.getDefaultInstance();
        getAllTerceiros();
}


    private void getAllTerceiros(){
        RealmResults<Terceiro> results = realm.where(Terceiro.class).findAll();
        layoutManager = new LinearLayoutManager(this);
        // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setHasFixedSize(true);
        terceiroAdapter = new TerceiroAdapter(this, realm, results);
        recyclerView.setAdapter(terceiroAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
            realm.close();
    }
}
