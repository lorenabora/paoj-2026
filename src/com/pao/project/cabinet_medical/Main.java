package com.pao.project.cabinet_medical;

import com.pao.project.cabinet_medical.model.*;
import com.pao.project.cabinet_medical.exception.*;
import com.pao.project.cabinet_medical.service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        ServiciuClient serviciuClient = ServiciuClient.getInstance();
        ServiciuMedic serviciuMedic = ServiciuMedic.getInstance();
        ServiciuProgramare serviciuProgramare = ServiciuProgramare.getInstance();

        //creez useri
        Client c1 = new Client(1, "Bora", "bora@gmail.com", "07707707777");
        Abonament abonament = new Abonament("PREMIUM", 80.0, 1800);
        c1.setAbonament(abonament);
        serviciuClient.adauga(c1);
        Client c2 = new Client(2, "Tanase", "tanase@gmail.com", "0744545545");
        serviciuClient.adauga(c2);

        Medic m1 = new Medic(1, "Dr. Rata", "rata@yahoo.com", "07121211212", TipMedic.ORTOPED, Arrays.asList("Luni 9-17", "Joi 10-18"));
        MedicSpecialist ms = new MedicSpecialist(2, "Dr. Popescu", "popescu@yahoo.com", "0712345678", TipMedic.OFTALMOLOG, Arrays.asList("Miercuri 10-18"), "Chirurgie oculara");
        serviciuMedic.adauga(m1);
        serviciuMedic.adauga(ms);

        //analize c1
        c1.adaugaAnaliza(new Analiza("Sange", "OK", LocalDate.now().minusDays(10)));
        c1.adaugaAnaliza(new Analiza("RMN", "Leziune de gradul I la menisc", LocalDate.now().minusDays(15)));

        try{
            //creare programare
            Programare p1 = serviciuProgramare.creareProgramare(c1, m1, LocalDateTime.now().plusDays(2), 300);
            Programare p2 = serviciuProgramare.creareProgramare(c2, ms, LocalDateTime.now().plusDays(20), 350);
            serviciuProgramare.adaugaProgramare(p1);
            serviciuProgramare.adaugaProgramare(p2);
            //listare medici
            System.out.println("Lista medici: ");
            for(Medic m : serviciuMedic.listeaza()) System.out.println(m);
            //istoric spital programari
            System.out.println("Istoric programari spital");
            for(Programare p : serviciuProgramare.afisareIstoricProgramari(c1)) System.out.println(p);
            //calculare plata
            double totalPlataC1 = serviciuProgramare.calculareDetaliiPlata(p1);
            double totalPlataC2 = serviciuProgramare.calculareDetaliiPlata(p2);
            System.out.println("Total plata "+c1.getNume()+": "+totalPlataC1+"\nTotal plata "+c2.getNume()+": "+totalPlataC2);
            //afisare rezultate analize
            serviciuProgramare.afisareRezultateAnalize(c1);
            //simulare export dosar medical
            serviciuProgramare.exportareIstoricMedical(c1);
            //afisare cont client
            serviciuProgramare.afisareContClient(c1);
            //afisare orar medic
            serviciuMedic.afiseazaOrarMedic(m1);
            //calc oferte
            serviciuProgramare.calculareOferte(c1);
            serviciuProgramare.calculareOferte(c2);
            //reprogramare/anulare
            System.out.println("Reprogramare pentru "+p1.getCod());
            serviciuProgramare.reprogramare(p1, LocalDateTime.now().plusDays(3));
            System.out.println("Data reprogramare: "+p1.getDataOra());
            System.out.println("Anulare pentru "+p2.getCod());
            serviciuProgramare.anulareProgramare(p2);
            System.out.println("Programarea "+p2.getCod()+" este "+p2.getStatus());
            serviciuProgramare.stergeProgramare(p2);
            //creare consultatie
            Consultatie consultatie = serviciuProgramare.creeazaConsultatie(p1, "leziune severa la cartilajul genunchiului stang","infiltratii PRP, fizioterapie");
            System.out.println("Consultatie creata: "+consultatie);
        }
        catch(DataIndisponibilaException | MedicIndisponibilException e){
            System.out.println("Eroare: "+e.getMessage());
        }
        //map pe clienti
        System.out.println("Toti cleintii");
        for(Client c : serviciuClient.listeaza()) System.out.println(c);
    }
}
