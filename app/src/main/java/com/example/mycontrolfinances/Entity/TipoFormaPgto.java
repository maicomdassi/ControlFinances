package com.example.mycontrolfinances.Entity;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipoFormaPgto extends RealmObject{

    @PrimaryKey
    private int id;
    private String nome;

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

    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
