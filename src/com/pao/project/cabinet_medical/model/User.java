package com.pao.project.cabinet_medical.model;

public abstract class User {
    protected int id;
    protected String nume, email, telefon;

    public User(int id, String nume, String email, String telefon){
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
    }
    public int getId(){ return id;}
    public String getNume() {return nume;}
    public String getEmail() {return email;}
    public String getTelefon() {return telefon;}

    public void setNume(String nume) { this.nume = nume;}
    public void setEmail(String email) { this.email = email;}
    public void setTelefon(String telefon) {this.telefon = telefon; }

    public abstract String getRol();
}
