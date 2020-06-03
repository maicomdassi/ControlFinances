package com.example.mycontrolfinances.view;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mycontrolfinances.Entity.CartaoCredito;
import com.example.mycontrolfinances.Entity.Categoria;
import com.example.mycontrolfinances.Entity.ContaCorrente;
import com.example.mycontrolfinances.Entity.Data;
import com.example.mycontrolfinances.Entity.MoneyTextWatcher;
import com.example.mycontrolfinances.Entity.Terceiro;
import com.example.mycontrolfinances.Entity.TipoFormaPgto;
import com.example.mycontrolfinances.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddContaPagarActivity extends AppCompatActivity{
    ChipGroup chipGroup;
    Chip chipPessoal,chipTerceiro;
    View view;
    TextInputLayout inputLayout;
    TextInputEditText txtDescricao, txtParcelas, txtValor, txtDtCompra,txtInputCategoria;
    TextView labelValorParcela,labelFaturaDe;
    ImageView btnInputCategoria;
    Button btnSalvaInputCategoria;

    MaterialSpinner cmb_Terceiros, cmb_FormaPgto, cmbFatura,cmb_TipoFormaPgto,cmbCategoria;
    CheckBox checkBox;
    MoneyTextWatcher mtw;
    Calendar calendar;

    ArrayAdapter arrayAdapter;
    private Realm realm;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conta_pagar);
        realm = Realm.getDefaultInstance();

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Conta a Pagar");

        btnInputCategoria = findViewById(R.id.addCategoria);

        calendar = Calendar.getInstance();
        chipPessoal = findViewById(R.id.chip_pessoal);
        chipTerceiro = findViewById(R.id.chip_terceiro);
        chipGroup = findViewById(R.id.chip_group);
        checkBox = findViewById(R.id.ckb_gerar);
        inputLayout = findViewById(R.id.inputValorCpag);

        cmb_Terceiros = (MaterialSpinner) findViewById(R.id.cmbTerceiros);
        cmb_FormaPgto = (MaterialSpinner) findViewById(R.id.cmbFormaPgto);
        cmb_TipoFormaPgto = (MaterialSpinner) findViewById(R.id.cmbTipoFormaPgto);
        cmbFatura = (MaterialSpinner) findViewById(R.id.cmbMesFatura);
        cmbCategoria = (MaterialSpinner) findViewById(R.id.cmbCategoria);

        labelValorParcela = findViewById(R.id.labelvalorParcelaCpag);
        labelFaturaDe = findViewById(R.id.labelFaturaDe);
        txtDescricao = findViewById(R.id.txtAddDescricaoCPag);
        txtParcelas = findViewById(R.id.txtParcelaCpag);
        txtValor = findViewById(R.id.txtValorCpag);
        txtDtCompra = findViewById(R.id.txtDtCompra);

        final int ano = calendar.get(calendar.YEAR);
        final int mes = calendar.get(calendar.MONTH);
        final int dia = calendar.get(calendar.DAY_OF_MONTH);

        String data =  String.valueOf(dia) +"/"+String.valueOf(mes)+"/"+String.valueOf(ano);
        txtDtCompra.setText(data);

        RealmResults<TipoFormaPgto> resultsTipo = realm.where(TipoFormaPgto.class).findAll();
        CarregaCmb(cmb_TipoFormaPgto,resultsTipo);

        CarregaCmbFatura();
        cmbFatura.setSelectedIndex(1);

        cmb_Terceiros.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);

        mtw = new MoneyTextWatcher(txtValor);
        txtValor.addTextChangedListener(mtw);


        cmb_TipoFormaPgto.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                switch (((TipoFormaPgto) item).getId()){
                    case 1 : {
                                RealmResults<CartaoCredito> results = realm.where(CartaoCredito.class).findAll();
                                CarregaCmb(cmb_FormaPgto,results);
                                cmb_FormaPgto.setVisibility(View.VISIBLE);
                                labelValorParcela.setVisibility(View.VISIBLE);
                                cmbFatura.setVisibility(View.VISIBLE);
                                labelFaturaDe.setVisibility(View.VISIBLE);
                                break;
                    }
                    case 2 : {
                                RealmResults<ContaCorrente> results = realm.where(ContaCorrente.class).findAll();
                                CarregaCmb(cmb_FormaPgto,results);
                                cmb_FormaPgto.setVisibility(View.VISIBLE);
                                labelValorParcela.setVisibility(View.GONE);
                                labelFaturaDe.setVisibility(View.GONE);
                                cmbFatura.setVisibility(View.GONE);
                                break;
                    }
                    case 3 : {
                                cmb_FormaPgto.setVisibility(View.GONE);
                                labelValorParcela.setVisibility(View.GONE);
                                cmbFatura.setVisibility(View.GONE);
                                labelFaturaDe.setVisibility(View.GONE);
                                break;
                    }
                }
            }
        });
        txtDtCompra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddContaPagarActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                       // month = month+1;
                        String data = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                        txtDtCompra.setText(data);
                    }
                },ano,mes,dia);
                datePickerDialog.show();
            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {

                if (group.getCheckedChipId() == chipPessoal.getId()){

                    cmb_Terceiros.setVisibility(View.GONE);
                    checkBox.setVisibility(View.GONE);

                }
                if (group.getCheckedChipId() == chipTerceiro.getId()){
                    RealmResults<Terceiro> resultsTerceiros = realm.where(Terceiro.class).findAll();
                    CarregaCmb(cmb_Terceiros,resultsTerceiros);
                    cmb_Terceiros.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                }

            }
        });



        txtParcelas.addTextChangedListener(new TextWatcher(){
                                               @Override
                                               public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                               }

                                               @Override
                                               public void onTextChanged(CharSequence s, int start, int before, int count) {

                                               }

                                               @Override
                                               public void afterTextChanged(Editable s) {
                                                   String valor = s.toString();
                                                   if (valor.isEmpty()){
                                                       labelValorParcela.setText(CalculaParcelas(0));
                                                   }else
                                                       labelValorParcela.setText(CalculaParcelas(Integer.parseInt(valor)));
                                               }
                                           }

        );

        txtValor.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    CalculaParcelas(Integer.parseInt(txtParcelas.getText().toString().trim()));
                }
            }
        });
        cmb_Terceiros.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        btnInputCategoria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DisplayInputDialog();
            }
        });

    }

    private String CalculaParcelas(int parcela){
        if (txtValor.getText().toString().trim() == "" ||
                txtValor.getText().toString().trim() == "0" ||
                parcela == 0){
            return "";
        }else{
            double valor = Double.parseDouble(mtw.formatPriceSave( txtValor.getText().toString().trim()));

                 if (parcela == 1){
                     return "1x de R$ "+ txtValor.getText().toString().trim();
                 }else{
                     double vCalculado = (valor/parcela);
                     String retorno = "";
                     DecimalFormat df = new DecimalFormat("#,###.00");
                     retorno = String.valueOf(parcela) + "x de R$ ";
                     retorno += df.format(vCalculado);
                    return retorno;
                 }

        }

    }

    private void CarregaCmb(MaterialSpinner cmb, RealmResults results){

        arrayAdapter = new ArrayAdapter(AddContaPagarActivity.this,R.layout.support_simple_spinner_dropdown_item,results);
        cmb.setAdapter(arrayAdapter);
    }

    private void CarregaCmbFatura(){

        ArrayList<Data> array = new ArrayList<>();
        LocalDate data = LocalDate.now();

        data = data.plusMonths(-1);
        Data dt = new Data(data);
        array.add(dt);

        for (int i = 0; i <= 1; i++)
        {
            if (i == 0){
                data = LocalDate.now();
                 dt = new Data(data);
                array.add(dt);
            }else {
                data = data.plusMonths(i);
                 dt = new Data(data);
                array.add(dt);
            }
        }

        arrayAdapter = new ArrayAdapter(AddContaPagarActivity.this,R.layout.support_simple_spinner_dropdown_item,array);
        cmbFatura.setAdapter(arrayAdapter);
    }

    private void DisplayInputDialog(){
        Dialog d = new Dialog(this);
        d.setTitle("Add Categoria");
        d.setContentView(R.layout.input_dialog_categoria);

        txtInputCategoria = d.findViewById(R.id.txtAddInputCategoria);
        btnSalvaInputCategoria = d.findViewById(R.id.btn_addInputCategoria);

        d.show();
        btnSalvaInputCategoria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Categoria categoria = new Categoria();
                categoria.setNome(txtInputCategoria.getText().toString().trim());


            }
        });
    }

}
