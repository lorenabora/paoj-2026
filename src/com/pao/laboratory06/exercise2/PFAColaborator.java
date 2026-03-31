package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements IOperatiiCitireScriere{
    private double cheltuieli;

    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net = (venit_brut_lunar - cheltuieli) * 12;
        // Impozit pe venit: 10%
        double impozit_venit = 0.1 * venit_net;
        // CASS (10%)
        double cass;
        if (venit_net < 6 * SALARIU_MINIM_AN) {
            cass = 0.1 * (6 * SALARIU_MINIM_AN);
        } else if (venit_net <= 72 * SALARIU_MINIM_AN) {
            cass = 0.1 * venit_net;
        } else {
            cass = 0.1 * (72 * SALARIU_MINIM_AN);
        }
        // CAS (25%)
        double cas;
        if (venit_net < 12 * SALARIU_MINIM_AN) {
            cas = 0;
        } else if (venit_net <= 24 * SALARIU_MINIM_AN) {
            cas = 0.25 * (12 * SALARIU_MINIM_AN);
        } else {
            cas = 0.25 * (24 * SALARIU_MINIM_AN);
        }
        return venit_net - impozit_venit - cass - cas;
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
        System.out.printf("%s: %s %s, venit net anual: %.2f lei\n", getTip(), nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "PFA";
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }
}
