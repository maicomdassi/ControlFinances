package com.example.mycontrolfinances.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.example.mycontrolfinances.Entity.CartaoCredito;

import com.example.mycontrolfinances.Entity.MoneyTextWatcher;
import com.example.mycontrolfinances.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddCartaoActivity extends AppCompatActivity{

    private TextInputEditText   txtnome_cartao,
                                txtlimite,
                                txtnumeros,
                                txtAnuidade,
                                txtbandeira;

    private AutoCompleteTextView txtMelhorDia,
                                 txtvencimento;

    private TextInputLayout comboMdia,
                            combovencimento;

    private Realm myRealm;
    private RealmAsyncTask realmTask;
    Toolbar toolbar;
    MoneyTextWatcher mtw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cartao);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro de Cartões");

        txtnome_cartao = findViewById(R.id.txtAddNomeCartao);
        txtlimite = findViewById(R.id.txtAddLimite);
        txtnumeros = findViewById(R.id.txtAddNumeros);
        txtMelhorDia = findViewById(R.id.txtAddMelhorDia);
        txtvencimento = findViewById(R.id.txtAddVencimento);
        txtbandeira = findViewById(R.id.txtAddBandeira);
        txtAnuidade = findViewById(R.id.txtAddAnuidade);

        comboMdia =findViewById(R.id.inputAddMelhorDia);
        combovencimento =findViewById(R.id.inputAddVencimento);

        mtw = new MoneyTextWatcher(txtlimite);
        txtlimite.addTextChangedListener(mtw);

        mtw = new MoneyTextWatcher(txtAnuidade);
        txtAnuidade.addTextChangedListener(mtw);

        ArrayList<String> array = new ArrayList<String>();

        for (int i = 1; i < 32; i++){
            array.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCartaoActivity.this,R.layout.list_item,array);

        txtMelhorDia.setAdapter(adapter);
        txtvencimento.setAdapter(adapter);

        myRealm = Realm.getDefaultInstance();

    }

    public void insertRecords() {

        final String name = txtnome_cartao.getText().toString();
        final String numeros = txtnumeros.getText().toString();
        final int mDia = Integer.parseInt(txtMelhorDia.getText().toString());
        final int vencimento = Integer.parseInt(txtvencimento.getText().toString());
        final String bandeira = txtbandeira.getText().toString();

        final double limite = Double.parseDouble(mtw.formatPriceSave(txtlimite.getText().toString().trim()));
        final double anuidade = Double.parseDouble(mtw.formatPriceSave(txtAnuidade.getText().toString().trim()));


        if (name.isEmpty()) {
            Toast.makeText(AddCartaoActivity.this, "Digite um Nome ...", Toast.LENGTH_LONG).show();
            return;
        }
        if (numeros.isEmpty()) {
            Toast.makeText(AddCartaoActivity.this, "Digite os 4 últimos digitos do cartão ...", Toast.LENGTH_LONG).show();
            return;
        }
        if (mDia <= 0 || mDia >= 32) {
            Toast.makeText(AddCartaoActivity.this, "Digite um dia do mês valido ...", Toast.LENGTH_LONG).show();
            return;
        }
        if (vencimento <= 0 || vencimento >= 32) {
            Toast.makeText(AddCartaoActivity.this, "Digite um Vencimento Valido...", Toast.LENGTH_LONG).show();
            return;
        }
        if (bandeira.isEmpty()) {
            Toast.makeText(AddCartaoActivity.this, "Digite uma bandeira ...", Toast.LENGTH_LONG).show();
            return;
        }
        if (limite <= 0) {
            Toast.makeText(AddCartaoActivity.this, "Digite um valor maior que zero ...", Toast.LENGTH_LONG).show();
            return;
        }

        realmTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number maxId = realm.where(CartaoCredito.class).max("id");
                        int newKey = (maxId == null) ? 1 :maxId.intValue()+1;

                        CartaoCredito cartaoCredito = realm.createObject(CartaoCredito.class,newKey);
                        cartaoCredito.setNome(name);
                        cartaoCredito.setNumero(numeros);
                        cartaoCredito.setVencimento(vencimento);
                        cartaoCredito.setBandeira(bandeira);
                        cartaoCredito.setLimite(limite);
                        cartaoCredito.setMelhorDia(mDia);
                        cartaoCredito.setTxAnuidade(anuidade);

                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddCartaoActivity.this,name+" adicionado com sucesso!", Toast.LENGTH_LONG).show();
                        //txtnome_cartao.setText("");
                        startActivity(new Intent(AddCartaoActivity.this , ListCartaoActivity.class));
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(AddCartaoActivity.this,"A Tentativa de adicionar "+name+" falhou!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void addCartao(View view) {
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
