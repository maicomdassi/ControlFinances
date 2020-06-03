package com.example.mycontrolfinances.Entity;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ContaCorrente extends RealmObject{

    @PrimaryKey
    private int id;

    @Required
    private String descricao;

    private long agencia;
    private int digAg;
    private long contaCorrente;
    private int digCc;
    private double saldo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getAgencia() {
        return agencia;
    }

    public void setAgencia(long agencia) {
        this.agencia = agencia;
    }

    public int getDigAg() {
        return digAg;
    }

    public void setDigAg(int digAg) {
        this.digAg = digAg;
    }

    public long getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(long contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public int getDigCc() {
        return digCc;
    }

    public void setDigCc(int digCc) {
        this.digCc = digCc;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @NonNull
    @Override
    public String toString() {
        return this.descricao+ "  Ag:"+this.agencia+"-"+this.digAg+  "  Cc:"+this.contaCorrente+"-"+this.digCc;
    }
}
