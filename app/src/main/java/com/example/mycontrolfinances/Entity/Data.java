package com.example.mycontrolfinances.Entity;

import android.text.format.DateUtils;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Date;

public class Data{

    private int dia;
    private int mes;
    private int ano;
    private String mesAbrev;

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMesAbrev() {
        return mesAbrev;
    }

    public void setMesAbrev(String mesAbrev) {
        this.mesAbrev = mesAbrev;
    }

    public Data(){}

    public Data(int dia,int mes, int ano){
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.mesAbrev = GetMesAbrv(this.mes);
    }

    public Data(LocalDate date){
        this.dia = date.getDayOfMonth();
        this.mes = date.getMonthValue();
        this.ano = date.getYear();
        this.mesAbrev = GetMesAbrv(this.mes);
    }

    public String GetMesAbrv(int mes){
        String mesAbrev = "";
        switch (mes) {
            case 1 : mesAbrev = "Jan"; break;
            case 2 : mesAbrev = "Fev"; break;
            case 3 : mesAbrev = "Mar"; break;
            case 4 : mesAbrev = "Abr"; break;
            case 5 : mesAbrev = "Mai"; break;
            case 6 : mesAbrev = "Jun"; break;
            case 7 : mesAbrev = "Jul"; break;
            case 8 : mesAbrev = "ago"; break;
            case 9 : mesAbrev = "Set"; break;
            case 10 : mesAbrev = "Out"; break;
            case 11 : mesAbrev = "Nov"; break;
            case 12 : mesAbrev = "Dez"; break;
        }
        return mesAbrev;
    }
    public String getMesAno(){
        return this.mesAbrev+"/"+this.ano;
    }

    @NonNull
    @Override
    public String toString() {
        return this.mesAbrev+"/"+this.ano;
    }
}
