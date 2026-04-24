package com.pao.project.cabinet_medical.model;

import com.pao.project.cabinet_medical.interfaces.IPlata;

public class Abonament implements IPlata {
    private String tip;
    private double reducereProcent, pretLunar;

    public Abonament(String tip, double reducereProcent, double pretLunar){
        this.tip = tip;
        this.reducereProcent = reducereProcent;
        this.pretLunar = pretLunar;
    }

    public String getTip() {return tip;}
    public double getReducereProcent() {return reducereProcent;}
    public double getPretLunar() {return pretLunar;}
    public void setTip(String tip) {this.tip = tip;}
    public void setPretLunar(double pretLunar) {this.pretLunar = pretLunar;}
    public void setReducereProcent(double reducereProcent) {this.reducereProcent = reducereProcent;}

    @Override
    public double calculeazaPretFinal(double pretBaza) {
        return pretBaza * (1-reducereProcent/100.0);
    }

    @Override
    public String toString(){
        return "Abonament{ tip= "+tip+", reducere(%)= "+reducereProcent+", pret lunar al abonamentului= "+pretLunar+"}";
    }
}
