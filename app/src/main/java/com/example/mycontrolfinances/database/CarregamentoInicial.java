package com.example.mycontrolfinances.database;
import com.example.mycontrolfinances.Entity.TipoFormaPgto;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class CarregamentoInicial{

    private Realm myRealm;
    private RealmAsyncTask realmTask;

    public CarregamentoInicial(){
        myRealm = Realm.getDefaultInstance();
        Carrega();
    }

    private void Carrega(){

        RealmResults<TipoFormaPgto> results = myRealm.where(TipoFormaPgto.class).findAll();
        if (results.isEmpty()){

            ArrayList<String> array = new ArrayList<>();
            array.add("Cartão de Crédito");
            array.add("Débito");
            array.add("Dinheiro");

            for (final String str : array) {
                realmTask = myRealm.executeTransactionAsync(
                        new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Number maxId = realm.where(TipoFormaPgto.class).max("id");
                                int newKey = (maxId == null) ? 1 :maxId.intValue()+1;

                                TipoFormaPgto tipo = realm.createObject(TipoFormaPgto.class,newKey);
                                tipo.setNome(str);

                            }
                        },
                        new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
         /*                   Toast.makeText(AddCartaoActivity.this,name+" adicionado com sucesso!", Toast.LENGTH_LONG).show();
                            //txtnome_cartao.setText("");
                            startActivity(new Intent(AddCartaoActivity.this , ListCartaoActivity.class));*/
                            }
                        },
                        new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                /*               Toast.makeText(AddCartaoActivity.this,"A Tentativa de adicionar "+name+" falhou!", Toast.LENGTH_LONG).show();*/
                            }
                        }
                );
            }

        }
    }
}
