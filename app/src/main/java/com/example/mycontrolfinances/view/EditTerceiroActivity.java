package com.example.mycontrolfinances.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mycontrolfinances.Entity.Terceiro;
import com.example.mycontrolfinances.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditTerceiroActivity extends AppCompatActivity{

    private EditText name;
    private Realm realm;
    private Bundle bundle;
    private int position;
    private Terceiro terceiro;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_terceiro);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Terceiros");


        bundle = getIntent().getExtras();
        if (bundle!=null){
            position = bundle.getInt("position");

            name = findViewById(R.id.txtNome_EditTerceiro);

    /*        Realm.init(getContext());
            RealmConfiguration configuration = new RealmConfiguration.Builder().name("terceiro.realm").build();
            Realm.setDefaultConfiguration(configuration);
*/
            realm = Realm.getDefaultInstance();
            RealmResults<Terceiro> results = realm.where(Terceiro.class).findAll();
            terceiro = results.get(position);
            setupView(terceiro);
        }

    }

    private void setupView(Terceiro terceiro) {
        name.setText(terceiro.getNome());
    }

    private void updateTerceiro(){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                terceiro.setNome(name.getText().toString().trim());

                startActivity(new Intent(EditTerceiroActivity.this, listaTerceirosActivity.class));
            }
        });
    }
    public void editTerceiro(View view) {
        updateTerceiro();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

