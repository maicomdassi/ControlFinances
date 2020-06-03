package com.example.mycontrolfinances.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycontrolfinances.Entity.ContaCorrente;
import com.example.mycontrolfinances.Entity.MoneyTextWatcher;
import com.example.mycontrolfinances.R;

import com.google.android.material.textfield.TextInputEditText;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddContaCorrenteActivity extends AppCompatActivity{

    private TextInputEditText txtDescricao,txtContaCorrente,txtAgencia,txtSaldo,txtdgAg,txtdgCc;
    private Realm myRealm;
    private RealmAsyncTask realmTask;
    Toolbar toolbar;
    MoneyTextWatcher mtw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conta_corrente);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro de Cartões");

        txtDescricao = findViewById(R.id.txtAddDescricaoCc);
        txtContaCorrente = findViewById(R.id.txtAddCc);
        txtAgencia = findViewById(R.id.txtAddAgencia);
        txtSaldo =  findViewById(R.id.txtAddSaldo);
        txtdgAg = findViewById(R.id.txtAddDgAgencia);
        txtdgCc = findViewById(R.id.txtAddDgCc);
        myRealm = Realm.getDefaultInstance();

/*        SimpleMaskFormatter smf = new SimpleMaskFormatter("#N.NNN,NN");
        MaskTextWatcher mtw = new MaskTextWatcher(txtSaldo, smf);*/

         mtw = new MoneyTextWatcher(txtSaldo);
        txtSaldo.addTextChangedListener(mtw);


    }

    public void insertRecords() {

        final String descricao = txtDescricao.getText().toString().trim();
        final String agencia = txtAgencia.getText().toString().trim();
        final String conta = txtContaCorrente.getText().toString().trim();
        String dgAg = txtdgAg.getText().toString().trim();
        String dgCc = txtdgCc.getText().toString().trim();

        final double saldo = Double.parseDouble(mtw.formatPriceSave(txtSaldo.getText().toString().trim()));

        if (descricao.isEmpty()) {
            Toast.makeText(AddContaCorrenteActivity.this, "Digite uma Descrição ...", Toast.LENGTH_LONG).show();
            return;
        }
        if (agencia.isEmpty()) {
            Toast.makeText(AddContaCorrenteActivity.this, "Digite uma Agância ...", Toast.LENGTH_LONG).show();
            return;
        }

        if (conta.isEmpty()) {
            Toast.makeText(AddContaCorrenteActivity.this, "Digite uma Conta Corrente ...", Toast.LENGTH_LONG).show();
            return;
        }
        if (saldo < 0) {
            Toast.makeText(AddContaCorrenteActivity.this, "Digite um valor valido ...", Toast.LENGTH_LONG).show();
            return;
        }
        if(dgAg.isEmpty()){ dgAg = "0";}
        if(dgCc.isEmpty()){ dgCc= "0";}

        final int final_dgAg = Integer.parseInt(dgAg);
        final int final_dgCc= Integer.parseInt(dgCc);

        realmTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number maxId = realm.where(ContaCorrente.class).max("id");
                        int newKey = (maxId == null) ? 1 :maxId.intValue()+1;

                        ContaCorrente contaCorrente = realm.createObject(ContaCorrente.class,newKey);
                        contaCorrente.setDescricao(descricao);
                        contaCorrente.setAgencia(Long.parseLong(agencia));
                        contaCorrente.setContaCorrente(Long.parseLong(conta));
                        contaCorrente.setSaldo(saldo);
                        contaCorrente.setDigAg(final_dgAg);
                        contaCorrente.setDigCc(final_dgCc);
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddContaCorrenteActivity.this,descricao+" adicionado com sucesso!", Toast.LENGTH_LONG).show();
                        //txtnome_cartao.setText("");
                        startActivity(new Intent(AddContaCorrenteActivity.this , ListContaCorrenteActivity.class));
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(AddContaCorrenteActivity.this,"A Tentativa de adicionar "+descricao+" falhou!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void addCc(View view) {
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
