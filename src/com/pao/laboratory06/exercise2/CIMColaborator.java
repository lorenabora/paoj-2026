package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements IOperatiiCitireScriere{
    private boolean Bonus;

    @Override
    public void citeste(Scanner in){
        nume = in.next();
        prenume = in.next();
        venit_brut_lunar = in.nextDouble();
        if( in.hasNext()){
            String bonus = in.next();
            Bonus = bonus.equalsIgnoreCase("DA");
        }
    }

    @Override
    public boolean areBonus() {
        return Bonus;
    }

    @Override
    public String tipContract() {
        return "CIM";
    }

    @Override
    public void afiseaza() {
        System.out.printf("%s: %s %s, venit net anual: %.2f lei\n", getTip(), nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }

    @Override
    public double calculeazaVenitNetAnual(){
        //venit net anual = salariu brut lunar × 12 × 0.55; dacă are bonus, se adaugă 10% la rezultat.
        double anuala = venit_brut_lunar * 12 * 0.55;
        return (Bonus)? anuala+anuala*0.1 : anuala;
    }
}
