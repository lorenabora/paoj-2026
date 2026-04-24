package com.pao.project.cabinet_medical.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Programare implements Comparable<Programare>{
    private Client client;
    private Medic medic;
    private LocalDateTime dataOra;
    private double pretBaza;
    private String status;
    private CodProgramare cod;

    public Programare(Client client, Medic medic, LocalDateTime dataOra, double pretBaza, CodProgramare cod){
        this.client = client;
        this.medic = medic;
        this.dataOra = dataOra;
        this.pretBaza = pretBaza;
        this.status = "ACTIVA";
        this.cod = cod;
    }

    public Client getClient() {return client;}
    public Medic getMedic() {return medic;}
    public LocalDateTime getDataOra() {return dataOra;}
    public double getPretBaza() {return pretBaza;}
    public String getStatus() {return status;}
    public CodProgramare getCod() {return cod;}
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra;}
    public void setStatus(String status) { this.status = status;}

    @Override
    public int compareTo(Programare o) {
        return this.dataOra.compareTo(o.dataOra);
    }

    @Override
    public String toString() {
        return "Programare{ cod= "+cod+", client= "+client.getNume()+", medic= "+medic.getNume()+", dataOra= "+dataOra+", pretBaza= "+pretBaza+", status= "+status+"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programare)) return false;
        Programare that = (Programare) o;
        return Objects.equals(cod, that.cod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod);
    }
}
