package com.pao.project.cabinet_medical.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends User{
    private Abonament abonament;
    private List<Programare> istoricProgramari = new ArrayList<>();
    private List<Analiza> analize = new ArrayList<>();
    public Client(int id, String nume, String email, String telefon) {
        super(id, nume, email, telefon);
    }

    public Abonament getAbonament() {return abonament;}
    public void setAbonament( Abonament abonament ){this.abonament = abonament;}
    public List<Programare> getIstoricProgramari(){ return istoricProgramari;}
    public List<Analiza> getAnalize(){ return analize;}

    public void adaugaProgramareIstoric(Programare prog){
        if (prog!=null){ istoricProgramari.add(prog);}
    }
    public void adaugaAnaliza(Analiza a){
        if(a!=null){ analize.add(a);}
    }

    @Override
    public String getRol() {
        return "CLIENT";
    }

    @Override
    public String toString(){
        return "Client{ id= "+id+", nume= "+nume+", email= "+email+", telefon= "+telefon+", abonament= "+abonament+"}";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
