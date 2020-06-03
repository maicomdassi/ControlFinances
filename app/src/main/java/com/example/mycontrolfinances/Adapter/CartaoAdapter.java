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
import com.example.mycontrolfinances.Entity.CartaoCredito;
import com.example.mycontrolfinances.R;
import com.example.mycontrolfinances.view.EditCartaoActivity;
import io.realm.Realm;
import io.realm.RealmResults;

public class CartaoAdapter extends RecyclerView.Adapter<CartaoAdapter.Holders>{

    private Context context;
    private Realm realm;
    private RealmResults<CartaoCredito> realmResults;
    private LayoutInflater inflater;
    private CartaoAdapter.Holders holders;


    public CartaoAdapter(Context context, Realm realm, RealmResults<CartaoCredito> realmResults){
        this.context = context;
        this.realm = realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_cartao,parent, false);
        holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull Holders holder, int position) {
        CartaoCredito cartaoCredito = realmResults.get(position);
        holder.getCartao(cartaoCredito,position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder{

        private int position;
        private TextView nome;
        private TextView numero;
        private TextView bandeira;
        private TextView vencimento;
        private ImageView deleteCartao;
        // private JustifiedTextView book_description;

        public Holders(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.cartao_name);
            numero = itemView.findViewById(R.id.numero_Cartao);
            bandeira = itemView.findViewById(R.id.bandeira_Cartao);
            vencimento = itemView.findViewById(R.id.vencimento_Cartao);

            //editTerceiro = itemView.findViewById(R.id.edit_image_View);
            deleteCartao = itemView.findViewById(R.id.delete_image_view);
        }

        public void getCartao(CartaoCredito cartaoCredito, int position){
            this.position = position;
           // String name = cartaoCredito.getNome();
            nome.setText(cartaoCredito.getNome());
            numero.setText(cartaoCredito.getNumero());
            bandeira.setText(cartaoCredito.getBandeira());
            vencimento.setText("Venc:"+ cartaoCredito.getVencimento());
        }

        public  void setListeners(){

            holders.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditCartaoActivity.class);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });


            deleteCartao.setOnClickListener(new View.OnClickListener(){
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
