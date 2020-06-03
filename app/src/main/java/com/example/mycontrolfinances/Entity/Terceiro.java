package com.example.mycontrolfinances.Entity;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Terceiro  extends RealmObject{

    @PrimaryKey
    private int id;
    @Required
    private String nome;

    public Terceiro(){}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}
