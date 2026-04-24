package com.pao.project.cabinet_medical.model;

import java.util.List;
import java.util.Objects;

public class Medic extends User{
    private TipMedic tip;
    private List<String> orar;

    public Medic(int id, String nume, String email, String telefon, TipMedic tip, List<String> orar) {
        super(id, nume, email, telefon);
        this.tip = tip;
        this.orar = orar;
    }

    public TipMedic getTip() {return tip;}
    public List<String> getOrar() {return orar;}
    public void setTip(TipMedic tip){ this.tip = tip;}
    public void setOrar(List<String> orar){ this.orar = orar;}

    @Override
    public String getRol() {
        return "MEDIC";
    }
    @Override
    public String toString() {
        return "Medic{ id= "+id+", nume= "+nume+", tip= "+tip+"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medic)) return false;
        Medic medic = (Medic) o;
        return id == medic.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
