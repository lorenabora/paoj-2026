package com.pao.project.cabinet_medical.service;

import com.pao.project.cabinet_medical.exception.DataIndisponibilaException;
import com.pao.project.cabinet_medical.exception.MedicIndisponibilException;
import com.pao.project.cabinet_medical.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServiciuProgramare {
    private static ServiciuProgramare instance;
    private final List<Programare> programari = new ArrayList<>();
    private final List<Consultatie> consultatii = new ArrayList<>();

    private ServiciuProgramare(){}
    public static ServiciuProgramare getInstance(){
        if (instance == null) instance = new ServiciuProgramare();
        return instance;
    }

    public Programare creareProgramare(Client client, Medic medic, LocalDateTime dataOra, double pretBaza) throws DataIndisponibilaException, MedicIndisponibilException {
        verificareDisponibilitateMedic(medic, dataOra);
        CodProgramare cod = new CodProgramare(UUID.randomUUID().toString());
        Programare p = new Programare(client, medic, dataOra, pretBaza, cod);
        programari.add(p);
        client.adaugaProgramareIstoric(p);
        return p;
    }

    public void verificareDisponibilitateMedic(Medic medic, LocalDateTime dataOra) throws DataIndisponibilaException, MedicIndisponibilException {
        for (Programare pr : programari) {
            if (pr.getMedic().equals(medic) && pr.getDataOra().equals(dataOra) && !"ANULATA".equals(pr.getStatus())) {
                throw new DataIndisponibilaException("Medicul are deja o programare la această dată.");
            }
        }
        if (medic.getOrar() == null || medic.getOrar().isEmpty()) {
            throw new MedicIndisponibilException("Medicul nu are orar definit.");
        }
    }

    // istoric client sortat cresc
    public List<Programare> afisareIstoricProgramari(Client client) {
        List<Programare> lista = new ArrayList<>(client.getIstoricProgramari());
        Collections.sort(lista);
        return lista;
    }
    public void anulareProgramare(Programare p) {
        if (p != null) {
            p.setStatus("ANULATA");
            programari.remove(p);
        }
    }
    public void reprogramare(Programare p, LocalDateTime nouaDataOra) throws DataIndisponibilaException, MedicIndisponibilException {
        if (p == null) return;
        verificareDisponibilitateMedic(p.getMedic(), nouaDataOra);
        p.setDataOra(nouaDataOra);
    }
    public double calculareDetaliiPlata(Programare p) {
        Client c = p.getClient();
        if (c.getAbonament() != null) {
            return c.getAbonament().calculeazaPretFinal(p.getPretBaza());
        }
        return p.getPretBaza();
    }
    public Consultatie creeazaConsultatie(Programare p, String diagnostic, String recomandari) {
        double costFinal = calculareDetaliiPlata(p);
        Consultatie c = new Consultatie(p, diagnostic, recomandari, costFinal, LocalDateTime.now());
        consultatii.add(c);
        return c;
    }
    public void afisareRezultateAnalize(Client client) {
        System.out.println("Rezultate analize pentru " + client.getNume() + ":");
        for (Analiza a : client.getAnalize()) {
            System.out.println(a);
        }
    }

    //exportare istoric medical (simulare)
    public void exportareIstoricMedical(Client client) {
        System.out.println("Export istoric medical pentru " + client.getNume() + "...");
        for (Programare p : client.getIstoricProgramari()) {
            System.out.println(p);
        }
        for (Analiza a : client.getAnalize()) {
            System.out.println(a);
        }
        System.out.println("Export finalizat (simulat).");
    }

    public void afisareContClient(Client client) {
        System.out.println("Cont client");
        System.out.println(client);
        System.out.println("Istoric programări:");
        for (Programare p : client.getIstoricProgramari()) {
            System.out.println(" - " + p);
        }
        System.out.println("Analize:");
        for (Analiza a : client.getAnalize()) {
            System.out.println(" - " + a);
        }
    }

    public void calculareOferte(Client client) {
        System.out.println("Oferte pentru clientul " + client.getNume() + ":");
        if (client.getAbonament() != null) {
            System.out.println(" - Abonament " + client.getAbonament().getTip() +
                    " cu reducere " + client.getAbonament().getReducereProcent() + "%");
        } else {
            System.out.println(" - Fara abonament. Recomandare: abonament BASIC cu reducere 10%.");
        }
    }

    public void adaugaProgramare(Programare p) {
        if (p != null) programari.add(p);
    }
    public void stergeProgramare(Programare p) {
        programari.remove(p);
    }
    public List<Programare> listeazaToateProgramarile() {
        List<Programare> lista = new ArrayList<>(programari);
        Collections.sort(lista);
        return lista;
    }
    public Programare cautaDupaCod(CodProgramare cod) {
        for (Programare p : programari) {
            if (p.getCod().equals(cod)) return p;
        }
        return null;
    }
}
