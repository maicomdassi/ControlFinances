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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontrolfinances.Entity.Terceiro;
import com.example.mycontrolfinances.R;
import com.example.mycontrolfinances.view.EditTerceiroActivity;
//import com.example.mycontrolfinances.app.EditActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class TerceiroAdapter extends RecyclerView.Adapter<TerceiroAdapter.Holders>{

    private Context context;
    private Realm realm;
    private RealmResults<Terceiro> realmResults;
    private LayoutInflater inflater;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Holders holders;


    public TerceiroAdapter(Context context, Realm realm, RealmResults<Terceiro> realmResults){
        this.context = context;
        this.realm = realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_terceiros,parent, false);
        holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull Holders holder, int position) {
        Terceiro terceiro = realmResults.get(position);
        holder.getTerceiros(terceiro,position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder{

        private int position;
        private TextView nome;
        private ImageView deleteTerceiro;
       // private JustifiedTextView book_description;

        public Holders(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.terceiro_name);
            //editTerceiro = itemView.findViewById(R.id.edit_image_View);
            deleteTerceiro = itemView.findViewById(R.id.delete_image_view);
        }

        public void getTerceiros(Terceiro terceiro, int position){
            this.position = position;
            String name = terceiro.getNome();
            nome.setText(name);
        }

        public  void setListeners(){

            holders.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditTerceiroActivity.class);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });


            deleteTerceiro.setOnClickListener(new View.OnClickListener(){
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
