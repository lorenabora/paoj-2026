package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Cerințele se află în Readme.md — secțiunea Exercise 3.");

        AngajatService job = AngajatService.getInstance();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");
            // citește opțiunea și execută acțiunea
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Nume: ");
                    String numeAng = scanner.next();
                    System.out.print("Salariu: ");
                    double salariu = scanner.nextDouble();
                    System.out.println("Nume departament: ");
                    String numeDep=scanner.next();
                    System.out.println("Locatie Dept: ");
                    String locatie=scanner.next();
                    Departament depart=new Departament(numeDep, locatie);
                    job.addAngajat(new Angajat(numeAng, depart, salariu));
                    break;
                case 2:
                    job.listBySalary();
                    break;
                case 3:
                    System.out.println("Nume departament: ");
                    String numeDept=scanner.next();
                    job.findByDepartament(numeDept);
                    break;
                case 0:
                    System.out.println("La revedere!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }
}
