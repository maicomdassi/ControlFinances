package com.example.mycontrolfinances.Entity;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CartaoCredito extends RealmObject{

    @PrimaryKey
    private int id;
    @Required
    private String nome;

    @Required
    private String numero;
    private int diaVencimento;
    private int melhorDia;

    @Required
    private String bandeira;
    private double limite;
    private double txAnuidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getVencimento() {
        return diaVencimento;
    }

    public void setVencimento(int vencimento) {
        this.diaVencimento = vencimento;
    }

    public int getMelhorDia() {
        return melhorDia;
    }

    public void setMelhorDia(int melhorDia) {
        this.melhorDia = melhorDia;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getTxAnuidade() {
        return txAnuidade;
    }

    public void setTxAnuidade(double txAnuidade) {
        this.txAnuidade = txAnuidade;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome + "  " + this.numero;
    }
}
