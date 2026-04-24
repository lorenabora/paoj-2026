package com.pao.project.cabinet_medical.model;

import java.time.LocalDateTime;

public class Consultatie {
    private final Programare programare;
    private final String diagnostic;
    private final String recomandari;
    private final double costFinal;
    private final LocalDateTime dataConsultatie;

    public Consultatie(Programare programare, String diagnostic, String recomandari, double costFinal, LocalDateTime data){
        this.programare = programare;
        this.diagnostic = diagnostic;
        this.recomandari = recomandari;
        this.costFinal = costFinal;
        this.dataConsultatie = data;
    }

    public Programare getProgramare() {return programare;}
    public String getDiagnostic() {return diagnostic;}
    public String getRecomandari() {return recomandari;}
    public double getCostFinal() {return costFinal;}
    public LocalDateTime getDataEfectuare() {return dataConsultatie;}

    @Override
    public String toString() {
        return "Consultatie{ programare= "+programare.getCod()+", diagnostic= "+diagnostic+", costFinal= "+costFinal+", data= "+dataConsultatie+"}";
    }
}