package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        List<Student> studenti = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while((line = br.readLine()) != null){
            String[] elemente = line.split(",");
            String nume = elemente[0];
            int varsta = Integer.parseInt(elemente[1]);
            String oras = elemente[2];
            String strada = elemente[3];
            studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
        }
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        Scanner sc = new Scanner(System.in);
        String comandaCitita= sc.nextLine().trim();
        String[] parti = comandaCitita.split(" ");
        String comanda = parti[0];
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează

            if (comanda.equals("PRINT")){
                for (Student s: studenti){
                    System.out.println(s);
                }
            }
            else if (comanda.equals("SHALLOW")){
                if (parti.length < 2) return;
                String nume = parti[1];
                Student original = find(studenti, nume);
                Student clona = original.shallowClone();
                clona.getAdresa().setOras("MODIFICAT");
                System.out.println("Original: " + original);
                System.out.println("Clona: " + clona);
            }
            else if(comanda.equals("DEEP")){
                if (parti.length < 2) return;
                String nume = parti[1];
                Student original = find(studenti, nume);
                Student clona = (Student) original.clone();
                clona.getAdresa().setOras("MODIFICAT");
                System.out.println("Original: " + original);
                System.out.println("Clona: " + clona);
            }
        br.close();

    }
    private static Student find(List<Student> list, String nume) {
        for (Student s : list)
            if (s.getNume().equals(nume))  return s;
        return null;
    }
}
