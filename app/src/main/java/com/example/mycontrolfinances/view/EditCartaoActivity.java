package com.example.mycontrolfinances.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.mycontrolfinances.Entity.CartaoCredito;
import com.example.mycontrolfinances.Entity.MoneyTextWatcher;
import com.example.mycontrolfinances.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditCartaoActivity extends AppCompatActivity{

    private TextInputEditText   txtnome_cartao,
                                txtlimite,
                                txtnumeros,
                                txtanuidade,
                                txtbandeira;

    private AutoCompleteTextView txtmelhorDia,
                                 txtvencimento;

    private Realm realm;
    private Bundle bundle;
    private int position;
    private Toolbar toolbar;
    private CartaoCredito cartaoCredito;
    MoneyTextWatcher mtw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cartao);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Cartões");

        bundle = getIntent().getExtras();
        if (bundle!=null){
            position = bundle.getInt("position");

            txtnome_cartao = findViewById(R.id.txtEditNomeCartao);
            txtlimite = findViewById(R.id.txtEditLimite);
            txtnumeros = findViewById(R.id.txtEditNumeros);
            txtmelhorDia = findViewById(R.id.txtEditMelhorDia);
            txtvencimento = findViewById(R.id.txtEditVencimento);
            txtbandeira = findViewById(R.id.txtEditBandeira);
            txtanuidade = findViewById(R.id.txtEditAnuidade);

            mtw = new MoneyTextWatcher(txtlimite);
            txtlimite.addTextChangedListener(mtw);

            mtw = new MoneyTextWatcher(txtanuidade);
            txtanuidade.addTextChangedListener(mtw);

            ArrayList<String> array = new ArrayList<String>();

            for (int i = 1; i < 32; i++){
                array.add(String.valueOf(i));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(EditCartaoActivity.this,R.layout.list_item,array);

            txtmelhorDia.setAdapter(adapter);
            txtvencimento.setAdapter(adapter);

            realm = Realm.getDefaultInstance();
            RealmResults<CartaoCredito> results = realm.where(CartaoCredito.class).findAll();
            cartaoCredito = results.get(position);
            setupView(cartaoCredito);

        }

    }
    private void setupView(CartaoCredito cartaoCredito) {
        txtnome_cartao.setText(cartaoCredito.getNome());
        txtnumeros.setText(cartaoCredito.getNumero());
        txtbandeira.setText(cartaoCredito.getBandeira());
        txtlimite.setText(mtw.formatTextPrice(String.valueOf(cartaoCredito.getLimite())));
        txtanuidade.setText(mtw.formatTextPrice(String.valueOf(cartaoCredito.getTxAnuidade())));
        txtvencimento.setText(String.valueOf(cartaoCredito.getVencimento()));
        txtmelhorDia.setText(String.valueOf(cartaoCredito.getMelhorDia()));
    }

    private void updateCartao(){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                cartaoCredito.setNome(txtnome_cartao.getText().toString().trim());
                cartaoCredito.setNumero(txtnumeros.getText().toString().trim());
                cartaoCredito.setVencimento(Integer.parseInt(txtvencimento.getText().toString().trim()));
                cartaoCredito.setLimite(Double.parseDouble(mtw.formatPriceSave(txtlimite.getText().toString().trim())));
                cartaoCredito.setTxAnuidade(Double.parseDouble(mtw.formatPriceSave(txtanuidade.getText().toString().trim())));
                cartaoCredito.setMelhorDia(Integer.parseInt(txtmelhorDia.getText().toString().trim()));
                cartaoCredito.setBandeira(txtbandeira.getText().toString().trim());

                startActivity(new Intent(EditCartaoActivity.this, ListCartaoActivity.class));
            }
        });
    }
    public void editCartao(View view) {
        updateCartao();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
