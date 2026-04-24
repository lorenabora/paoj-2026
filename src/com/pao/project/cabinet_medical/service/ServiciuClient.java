package com.pao.project.cabinet_medical.service;

import com.pao.project.cabinet_medical.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiciuClient {
    private static ServiciuClient instance;
    private final Map<Integer, Client> clienti= new HashMap<>();

    private ServiciuClient(){}
    public static ServiciuClient getInstance(){
        if(instance == null){
            instance = new ServiciuClient();
        }
        return instance;
    }

    public void adauga(Client c){
        if (c!=null) clienti.put(c.getId(), c);
    }
    public void sterge(int id){ clienti.remove(id);}
    public Client cautaDupaId(int id){ return clienti.get(id);}
    public List<Client> cautaDupaNume(String nume){
        List<Client> rez = new ArrayList<>();
        for(Client c: clienti.values()){
            if (c.getNume().equalsIgnoreCase(nume)){ rez.add(c);}
        }
        return rez;
    }
    public List<Client> listeaza(){ return new ArrayList<>(clienti.values());}
}