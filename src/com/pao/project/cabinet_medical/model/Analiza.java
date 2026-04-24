package com.pao.project.cabinet_medical.model;

import java.time.LocalDate;

public class Analiza {
    private String tip, rezultat;
    private LocalDate data;

    public Analiza(String tip, String rezultat, LocalDate data){
        this.tip = tip;
        this.rezultat = rezultat;
        this.data = data;
    }

    public String getTip() {return tip;}
    public String getRezultat() {return rezultat;}
    public LocalDate getData() {return data;}
    public void setTip(String tip) {this.tip = tip;}
    public void setRezultat(String rezultat) {this.rezultat = rezultat;}
    public void setData(LocalDate data) {this.data = data;}

    @Override
    public String toString() {
        return "Analiza{ tip= "+tip+", rezultat= "+rezultat+", data= "+data+"}";
    }
}