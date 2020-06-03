package com.example.mycontrolfinances.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mycontrolfinances.Entity.CartaoCredito;
import com.example.mycontrolfinances.Entity.ContaCorrente;
import com.example.mycontrolfinances.Entity.MoneyTextWatcher;
import com.example.mycontrolfinances.R;
import com.google.android.material.textfield.TextInputEditText;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditContaCorrenteActivity extends AppCompatActivity{

    private Realm realm;
    private Bundle bundle;
    private int position;
    private Toolbar toolbar;
    private TextInputEditText txtDescricao,txtContaCorrente,txtAgencia,txtSaldo,txtdgAg,txtdgCc;
    private ContaCorrente contaCorrente;
    MoneyTextWatcher mtw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_conta_corrente);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Conta Corrente");

        bundle = getIntent().getExtras();
        if (bundle!=null){
            position = bundle.getInt("position");

            txtDescricao = findViewById(R.id.txtAddDescricaoCc);
            txtContaCorrente = findViewById(R.id.txtAddCc);
            txtAgencia = findViewById(R.id.txtAddAgencia);
            txtSaldo =  findViewById(R.id.txtAddSaldo);
            txtdgAg = findViewById(R.id.txtAddDgAgencia);
            txtdgCc = findViewById(R.id.txtAddDgCc);

            mtw = new MoneyTextWatcher(txtSaldo);
            txtSaldo.addTextChangedListener(mtw);

            realm = Realm.getDefaultInstance();

            RealmResults<ContaCorrente> results = realm.where(ContaCorrente.class).findAll();
            contaCorrente = results.get(position);
            setupView(contaCorrente);
        }
    }

    private void setupView(ContaCorrente contaCorrente) {

        txtDescricao.setText(contaCorrente.getDescricao());
        txtContaCorrente.setText(String.valueOf(contaCorrente.getContaCorrente()));
        txtAgencia.setText(String.valueOf(contaCorrente.getAgencia()));
        txtSaldo.setText(mtw.formatTextPrice(String.valueOf(contaCorrente.getSaldo())));
        txtdgAg.setText(String.valueOf(contaCorrente.getDigAg()));
        txtdgCc.setText(String.valueOf(contaCorrente.getDigCc()));
    }

    private void updateConta(){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                contaCorrente.setDescricao(txtDescricao.getText().toString().trim());
                contaCorrente.setAgencia(Integer.parseInt(txtAgencia.getText().toString().trim()));
                contaCorrente.setContaCorrente(Integer.parseInt(txtContaCorrente.getText().toString().trim()));
                contaCorrente.setSaldo(Double.parseDouble(mtw.formatPriceSave(txtSaldo.getText().toString().trim())));
                contaCorrente.setDigCc(Integer.parseInt(txtdgCc.getText().toString().trim()));
                contaCorrente.setDigAg(Integer.parseInt(txtdgAg.getText().toString().trim()));

                startActivity(new Intent(EditContaCorrenteActivity.this, ListContaCorrenteActivity.class));
            }
        });
    }
    public void EditCc(View view) {
        updateConta();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
