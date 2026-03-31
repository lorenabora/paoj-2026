package com.pao.laboratory06.exercise2;

public abstract class Colaborator {
    protected String nume, prenume;
    protected double venit_brut_lunar;
    protected static final double SALARIU_MINIM_AN = 4050 * 12;

    public Colaborator(){}

    public abstract TipColaborator getTip();

    public abstract void afiseaza();

    public abstract double calculeazaVenitNetAnual();
}
