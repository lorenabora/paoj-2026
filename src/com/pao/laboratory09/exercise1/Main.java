package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>(n);
        for(int i=0; i<n; i++){
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next(); String contSursa = scanner.next(); String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);

            t.setNote("procesat");
            tranzactii.add(t);
        }
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        //folder output existent
        File outputDir = new File("output");
        if (!outputDir.exists()) outputDir.mkdir();

        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(tranzactii);
        }

        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        List<Tranzactie> deserializareTranzactii;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            deserializareTranzactii = (List<Tranzactie>) ois.readObject();
        }
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        while(scanner.hasNext()){
            String comanda = scanner.next();
            switch (comanda){
                case "LIST":
                    for (Tranzactie t : deserializareTranzactii) {
                        System.out.println(t);
                    }
                    break;
                case "FILTER":
                    String prefix = scanner.next();
                    boolean found = false;
                    for (Tranzactie t : deserializareTranzactii) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Niciun rezultat.");
                    break;
                case "NOTE":
                    if (scanner.hasNext()){
                        int id = scanner.nextInt();
                        Tranzactie greseala =null;
                        for (Tranzactie t : deserializareTranzactii) {
                            if (t.getId() == id) {
                                greseala = t;
                                break;
                            }
                        }
                        if (greseala != null) {
                            System.out.println("NOTE[" + id + "]: " + greseala.getNote());
                        } else {
                            System.out.println("NOTE[" + id + "]: not found");
                        }
                    }
                    break;
            }
//            scanner.close();
        }
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

//        System.out.println("TODO: implementează exercițiul 1");
    }
}
