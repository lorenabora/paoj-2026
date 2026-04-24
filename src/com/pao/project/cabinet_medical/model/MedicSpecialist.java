package com.pao.project.cabinet_medical.model;

import java.util.List;

public class MedicSpecialist extends Medic{
    private String specializare;

    public MedicSpecialist(int id, String nume, String email, String telefon, TipMedic tip, List<String> orar, String specializare) {
        super(id, nume, email, telefon, tip, orar);
        this.specializare = specializare;
    }
    public String getSpecializare(){ return specializare; }
    public void setSpecializare(String specializare) {this.specializare = specializare;}

    @Override
    public String getRol() {
        return "MEDIC_SPECIALIST";
    }

    @Override
    public String toString() {
        return "MedicSpecialist{ id="+getId()+", nume= "+getNume()+", specializare= "+specializare+ "}";
    }
}
