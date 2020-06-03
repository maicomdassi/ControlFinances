package com.example.mycontrolfinances.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontrolfinances.Adapter.TerceiroAdapter;
import com.example.mycontrolfinances.Entity.Terceiro;
import com.example.mycontrolfinances.R;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class AddTerceiroActivity extends AppCompatActivity{

    private EditText nome_terceiro;
    private Realm myRealm;
    private RealmAsyncTask realmTask;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terceiro);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro de Terceiros");

        nome_terceiro = findViewById(R.id.txtAddNomeTerceiros);
        myRealm = Realm.getDefaultInstance();

    }

    public void insertRecords() {

        //RealmResults<Terceiro> results = myRealm.where(Terceiro.class).findAll();
/*        TerceiroAdapter adapter = new TerceiroAdapter(AddTerceiroActivity.this,myRealm,results);
        final int id = (adapter.getItemCount() + 1);*/
        // final int id = Integer.parseInt(UUID.randomUUID().toString());

        final String name = nome_terceiro.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(AddTerceiroActivity.this, "Digite um Nome ...", Toast.LENGTH_LONG).show();
            return;
        }

        realmTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number maxId = realm.where(Terceiro.class).max("id");
                        int newKey = (maxId == null) ? 1 :maxId.intValue()+1;

                        Terceiro terceiro = realm.createObject(Terceiro.class,newKey);
                        terceiro.setNome(name);
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddTerceiroActivity.this,name+" adicionado com sucesso!", Toast.LENGTH_LONG).show();
                        nome_terceiro.setText("");
                        startActivity(new Intent(AddTerceiroActivity.this , listaTerceirosActivity.class));
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(AddTerceiroActivity.this,"A Tentativa de adicionar "+name+" falhou!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void addTerceiros(View view) {
        insertRecords();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (realmTask != null && realmTask.isCancelled()){}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
