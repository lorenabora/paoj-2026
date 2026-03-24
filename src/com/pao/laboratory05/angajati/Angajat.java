package com.pao.laboratory05.angajati;

public class Angajat implements Comparable<Angajat>{
    private String nume;
    private Departament departament;
    private double salariu;

    public Angajat(String nume, Departament departament, double salariu){
        this.nume=nume;
        this.departament=departament;
        this.salariu=salariu;
    }

    public double getSalariu(){return this.salariu;}
    public String getNume(){return this.nume;}
    public Departament getDepartament(){return this.departament;}

    @Override
    public String toString(){
        return "Angajat{nume="+this.nume+", departament=Departament[nume="+this.departament.nume()+", locatie="+this.departament.locatie()+"], salariu="+this.salariu+"}";
    }

    @Override
    public int compareTo(Angajat other){
        return Double.compare(this.getSalariu(), other.getSalariu());
    }

}
