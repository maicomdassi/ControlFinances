package com.example.mycontrolfinances.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontrolfinances.view.EditContaCorrenteActivity;
import com.example.mycontrolfinances.Entity.ContaCorrente;
import com.example.mycontrolfinances.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class ContaCorrenteAdapter extends RecyclerView.Adapter<ContaCorrenteAdapter.Holders>{

    private Context context;
    private Realm realm;
    private RealmResults<ContaCorrente> realmResults;
    private LayoutInflater inflater;
    private ContaCorrenteAdapter.Holders holders;

    public ContaCorrenteAdapter(Context context, Realm realm, RealmResults<ContaCorrente> realmResults){
        this.context = context;
        this.realm = realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_conta_corrente,parent, false);
        holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull ContaCorrenteAdapter.Holders holder, int position) {
        ContaCorrente contaCorrente = realmResults.get(position);
        holder.getContaCorrente(contaCorrente,position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder{

        private int position;
        private TextView descricao;
        private TextView numagencia;
        private TextView numcontaCorrente;
        // private TextView saldo;
        private ImageView deleteCc;
        // private JustifiedTextView book_description;

        public Holders(@NonNull View itemView) {
            super(itemView);

            descricao = itemView.findViewById(R.id.Cc_descricao);
            numagencia = itemView.findViewById(R.id.numAgencia);
            numcontaCorrente = itemView.findViewById(R.id.numCc);
            //saldo = itemView.findViewById(R.id.);

            //editTerceiro = itemView.findViewById(R.id.edit_image_View);
            deleteCc = itemView.findViewById(R.id.delete_image_view);
        }

        public void getContaCorrente(ContaCorrente contaCorrente, int position){
            this.position = position;
            // String name = cartaoCredito.getNome();
            descricao.setText(contaCorrente.getDescricao());

            String dgAg = String.valueOf(contaCorrente.getDigAg());
            String dgCc = String.valueOf(contaCorrente.getDigCc());

            if (!dgAg.isEmpty() && dgAg != ""){
                dgAg = "-"+ dgAg;
            }else {dgAg = "";}
            if (!dgCc.isEmpty() && dgCc != ""){
                dgCc = "-"+ dgCc;
            }else {dgCc = "";}

            numagencia.setText("Ag:"+String.valueOf(contaCorrente.getAgencia())+dgAg );
            numcontaCorrente.setText("Cc:"+String.valueOf(contaCorrente.getContaCorrente())+dgCc);
            //saldo.setText(String.valueOf( contaCorrente.getSaldo()));
        }

        public  void setListeners(){

            holders.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditContaCorrenteActivity.class);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });


            deleteCc.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            realmResults.deleteFromRealm(position);
                            Toast.makeText(context,"Delete Records Successfully",Toast.LENGTH_LONG).show();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,realmResults.size());
                        }
                    });
                }
            });
        }


    }
}
