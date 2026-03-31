package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends Colaborator implements IOperatiiCitireScriere{
    private double cheltuieli;
    @Override
    public double calculeazaVenitNetAnual(){
        //venit net anual = (venit lunar - cheltuieli lunare) × 12 × 0.84 (se aplică doar impozit pe profit de 16%).
        return (venit_brut_lunar - cheltuieli) * 12 * 0.84;
    }

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venit_brut_lunar = in.nextDouble();
        if( in.hasNext()){
            cheltuieli = in.nextDouble();
        }
    }

    @Override
    public void afiseaza() {
        System.out.printf("SRL: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());}

    @Override
    public String tipContract() {
        return "SRL";
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.SRL;
    }
}
