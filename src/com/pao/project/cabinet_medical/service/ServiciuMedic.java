package com.pao.project.cabinet_medical.service;

import com.pao.project.cabinet_medical.model.Medic;
import com.pao.project.cabinet_medical.model.TipMedic;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiciuMedic {
    private static ServiciuMedic instance;
    private final Set<Medic> medici = new HashSet<>();

    private ServiciuMedic(){}
    public static ServiciuMedic getInstance(){
        if (instance == null){
            instance = new ServiciuMedic();
        }
        return instance;
    }

    public void adauga(Medic m){
        if (m != null) {medici.add(m);}
    }
    public void sterge(Medic m){ medici.remove(m);}
    public List<Medic> listeaza()
    {
        ServiciuAudit.getInstance().logheaza("listare_medici");
        return new ArrayList<>(medici);
    }
    public List<Medic> filtreazaDupaTip(TipMedic tip) {
        List<Medic> rezultat = new ArrayList<>();
        for (Medic m : medici){
            if (m.getTip() == tip){ rezultat.add(m);}
        }
        return rezultat;
    }
    public void afiseazaOrarMedic(Medic medic) {
        ServiciuAudit.getInstance().logheaza("afisare_orar_medic");
        System.out.println("Orar pentru medicul "+medic.getNume()+":");
        for (String interval : medic.getOrar()){
            System.out.println(" - " + interval);
        }
    }
}