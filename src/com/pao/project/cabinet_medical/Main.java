package com.pao.project.cabinet_medical;

import com.pao.project.cabinet_medical.model.*;
import com.pao.project.cabinet_medical.exception.*;
import com.pao.project.cabinet_medical.service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("Toti clientii");
        for(Client c : serviciuClient.listeaza()) System.out.println(c);

        //------------------------Meniu switch -----------------------------

//        Scanner scanner = new Scanner(System.in);
//        int optiune;
//        do {
//            System.out.println("\nMENIU CABINET MEDICAL");
//            System.out.println("1. Creeaza programare");
//            System.out.println("2. Afiseaza toti medicii");
//            System.out.println("3. Afiseaza istoricul unui client");
//            System.out.println("4. Anuleaza programare");
//            System.out.println("5. Reprogrameaza programare");
//            System.out.println("6. Afiseaza analize client");
//            System.out.println("7. Exporta istoric medical");
//            System.out.println("8. Afiseaza cont client");
//            System.out.println("9. Afiseaza orar medic");
//            System.out.println("10. Calculeaza oferte");
//            System.out.println("11. Adauga client");
//            System.out.println("12. Adauga medic");
//            System.out.println("0. Iesire");
//            System.out.print("Alege optiunea: ");
//            optiune = scanner.nextInt();
//            scanner.nextLine();
//            switch (optiune) {
//                case 1:
//                    System.out.println("Nume client:");
//                    String numeClient = scanner.nextLine();
//                    List<Client> clientiGasiti = serviciuClient.cautaDupaNume(numeClient);
//                    if (clientiGasiti.isEmpty()) {
//                        System.out.println("Nu exista client cu acest nume.");
//                        break;
//                    }
//                    Client clientSelectat = clientiGasiti.get(0);
//                    System.out.println("Nume medic:");
//                    String numeMedic = scanner.nextLine();
//                    Medic medicSelectat = null;
//                    for (Medic m : serviciuMedic.listeaza()) {
//                        if (m.getNume().equalsIgnoreCase(numeMedic)) {
//                            medicSelectat = m;
//                            break;
//                        }
//                    }
//                    if (medicSelectat == null) {
//                        System.out.println("Nu exista medic cu acest nume.");
//                        break;
//                    }
//                    System.out.println("Introdu data si ora (format: yyyy-MM-dd HH:mm):");
//                    String dataOraStr = scanner.nextLine();
//                    System.out.println("Introdu pretul de baza:");
//                    double pretBaza = scanner.nextDouble();
//                    scanner.nextLine();
//                    try {
//                        LocalDateTime dataOra = LocalDateTime.parse(dataOraStr.replace(" ", "T"));
//                        Programare programareNoua = serviciuProgramare.creareProgramare(clientSelectat, medicSelectat, dataOra, pretBaza);
//                        System.out.println("Programare creata: " + programareNoua);
//                    } catch (Exception e) {
//                        System.out.println("Eroare: " + e.getMessage());
//                    }
//                    break;
//                case 2:
//                    System.out.println("Lista medicilor:");
//                    for (Medic m : serviciuMedic.listeaza()) {
//                        System.out.println(m);
//                    }
//                    break;
//                case 3:
//                    System.out.println("Nume client:");
//                    String numeClientIstoric = scanner.nextLine();
//                    List<Client> clientiIstoric = serviciuClient.cautaDupaNume(numeClientIstoric);
//                    if (clientiIstoric.isEmpty()) {
//                        System.out.println("Client inexistent.");
//                    } else {
//                        Client cIst = clientiIstoric.get(0);
//                        for (Programare p : serviciuProgramare.afisareIstoricProgramari(cIst)) {
//                            System.out.println(p);
//                        }
//                    }
//                    break;
//                case 4:
//                    // bafta aici sa stii codul unei programari T-T
//                    System.out.println("Cod programare de anulat:");
//                    String codAnulareStr = scanner.nextLine();
//                    CodProgramare codAnulare = new CodProgramare(codAnulareStr);
//                    Programare pAnulare = serviciuProgramare.cautaDupaCod(codAnulare);
//                    if (pAnulare == null) {
//                        System.out.println("Nu exista programare cu acest cod.");
//                    } else {
//                        serviciuProgramare.anulareProgramare(pAnulare);
//                        System.out.println("Programare anulata: " + pAnulare.getCod());
//                    }
//                    break;
//                case 5:
//                    System.out.println("Cod programare de reprogramat:");
//                    String codReprogStr = scanner.nextLine();
//                    CodProgramare codReprog = new CodProgramare(codReprogStr);
//                    Programare pReprog = serviciuProgramare.cautaDupaCod(codReprog);
//                    if (pReprog == null) {
//                        System.out.println("Nu exista programare cu acest cod.");
//                    } else {
//                        System.out.println("Noua data si ora (yyyy-MM-dd HH:mm):");
//                        String nouaDataOraStr = scanner.nextLine();
//                        try {
//                            LocalDateTime nouaDataOra = LocalDateTime.parse(nouaDataOraStr.replace(" ", "T"));
//                            serviciuProgramare.reprogramare(pReprog, nouaDataOra);
//                            System.out.println("Programare reprogramata: " + pReprog);
//                        } catch (Exception e) {
//                            System.out.println("Eroare: " + e.getMessage());
//                        }
//                    }
//                    break;
//                case 6:
//                    System.out.println("Nume client:");
//                    String numeClientAnalize = scanner.nextLine();
//                    List<Client> clientiAnalize = serviciuClient.cautaDupaNume(numeClientAnalize);
//                    if (!clientiAnalize.isEmpty()) {
//                        serviciuProgramare.afisareRezultateAnalize(clientiAnalize.get(0));
//                    } else {
//                        System.out.println("Client inexistent.");
//                    }
//                    break;
//                case 7:
//                    System.out.println("Nume client:");
//                    String numeClientExport = scanner.nextLine();
//                    List<Client> clientiExport = serviciuClient.cautaDupaNume(numeClientExport);
//                    if (!clientiExport.isEmpty()) {
//                        serviciuProgramare.exportareIstoricMedical(clientiExport.get(0));
//                    } else {
//                        System.out.println("Client inexistent.");
//                    }
//                    break;
//                case 8:
//                    System.out.println("Nume client:");
//                    String numeClientCont = scanner.nextLine();
//                    List<Client> clientiCont = serviciuClient.cautaDupaNume(numeClientCont);
//                    if (!clientiCont.isEmpty()) {
//                        serviciuProgramare.afisareContClient(clientiCont.get(0));
//                    } else {
//                        System.out.println("Client inexistent.");
//                    }
//                    break;
//                case 9:
//                    System.out.println("Nume medic:");
//                    String numeMedicOrar = scanner.nextLine();
//                    Medic medicOrar = null;
//                    for (Medic m : serviciuMedic.listeaza()) {
//                        if (m.getNume().equalsIgnoreCase(numeMedicOrar)) {
//                            medicOrar = m;
//                            break;
//                        }
//                    }
//                    if (medicOrar != null) {
//                        serviciuMedic.afiseazaOrarMedic(medicOrar);
//                    } else {
//                        System.out.println("Medic inexistent.");
//                    }
//                    break;
//                case 10:
//                    System.out.println("Nume client:");
//                    String numeClientOferte = scanner.nextLine();
//                    List<Client> clientiOferte = serviciuClient.cautaDupaNume(numeClientOferte);
//                    if (!clientiOferte.isEmpty()) {
//                        serviciuProgramare.calculareOferte(clientiOferte.get(0));
//                    } else {
//                        System.out.println("Client inexistent.");
//                    }
//                    break;
//                case 11:
//                    System.out.println("ID client:");
//                    int idNou = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.println("Nume:");
//                    String numeNou = scanner.nextLine();
//                    System.out.println("Email:");
//                    String emailNou = scanner.nextLine();
//                    System.out.println("Telefon:");
//                    String telNou = scanner.nextLine();
//                    Client clientNou = new Client(idNou, numeNou, emailNou, telNou);
//                    serviciuClient.adauga(clientNou);
//                    System.out.println("Client adaugat.");
//                    break;
//                case 12:
//                    System.out.println("ID medic:");
//                    int idMedNou = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.println("Nume:");
//                    String numeMedNou = scanner.nextLine();
//                    System.out.println("Email:");
//                    String emailMedNou = scanner.nextLine();
//                    System.out.println("Telefon:");
//                    String telMedNou = scanner.nextLine();
//                    System.out.println("Tip medic (CARDIOLOG/PEDIATRU/...):");
//                    String tipStr = scanner.nextLine();
//                    TipMedic tip = TipMedic.valueOf(tipStr.toUpperCase());
//                    System.out.println("Orar (separate prin virgula):");
//                    String orarStr = scanner.nextLine();
//                    List<String> orar = Arrays.asList(orarStr.split(","));
//                    Medic medicNou = new Medic(idMedNou, numeMedNou, emailMedNou, telMedNou, tip, orar);
//                    serviciuMedic.adauga(medicNou);
//                    System.out.println("Medic adaugat.");
//                    break;
//                case 0:
//                    System.out.println("Iesire...");
//                    break;
//                default:
//                    System.out.println("Optiune invalida.");
//            }
//        } while (optiune != 0);


    }
}
