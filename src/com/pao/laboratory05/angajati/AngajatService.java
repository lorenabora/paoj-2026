package com.pao.laboratory05.angajati;

import com.pao.laboratory05.biblioteca.BibliotecaService;
import com.pao.laboratory05.biblioteca.Carte;

import java.util.Arrays;

public class AngajatService {
//    Constructor privat, getInstance() cu Holder intern
//    Câmp: private Angajat[] angajati (inițializat new Angajat[0])
    private Angajat[] angajati;
    private AngajatService(){
        this.angajati=new Angajat[0];
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }
//    void addAngajat(Angajat a) — resize + adaugă + printează confirmare
    public void addAngajat(Angajat a){
        Angajat[] newAngajati = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, newAngajati, 0, this.angajati.length);
        newAngajati[newAngajati.length-1]=a;
        this.angajati = newAngajati;
    }
//    void printAll() — afișează toți angajații (ordinea din array, nesortat)
    public void printAll(){
        for (Angajat a: angajati){
            System.out.println(a);
        }
    }
//    void listBySalary() — clonează, Arrays.sort(copy), afișează (descrescător, natural)
    public void listBySalary(){
        Angajat[] copy = this.angajati.clone();
        Arrays.sort(copy);
        System.out.println(Arrays.toString(copy).replace(", A", "\nA"));
    }
//    void findByDepartament(String numeDept)
//    — parcurge array-ul,
//    afișează toți angajații al căror angajat.getDepartament().nume().equalsIgnoreCase(numeDept);
//    dacă nu găsește niciun angajat, afișează "Niciun angajat în departamentul: <numeDept>"
    public void findByDepartament(String numeDept){
        boolean found=false;
        for(Angajat angajat:angajati){
            if(angajat.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(angajat);
                found=true;
            }
        }
        if(!found){
            System.out.println("Niciun angajat în departamentul: "+numeDept);
        }
    }

}
