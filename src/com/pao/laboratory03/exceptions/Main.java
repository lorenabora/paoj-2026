package com.pao.laboratory03.exceptions;

import java.sql.SQLOutput;
import java.util.List;

/**
 * Exercițiul 3 — Excepții (checked, unchecked, custom)
 *
 * Creează în acest pachet (lângă Main.java) două clase de excepții custom,
 * apoi demonstrează-le aici.
 *
 * PASUL 1 — Creează InvalidAgeException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 2 — Creează DuplicateEntryException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 3 — În acest Main.java, implementează și demonstrează:
 *
 *   a) UNCHECKED EXCEPTIONS — NullPointerException, ArrayIndexOutOfBoundsException:
 *      - Creează o metodă riskyMethod() care aruncă NullPointerException
 *      - Prinde-o cu try-catch, afișează mesajul erorii
 *      - Adaugă un bloc finally care se execută mereu
 *
 *   b) CUSTOM EXCEPTIONS — InvalidAgeException, DuplicateEntryException:
 *      - Creează o metodă validateAge(int age) care aruncă InvalidAgeException
 *        dacă age < 0 sau age > 150
 *      - Creează o metodă addToList(List<String> list, String name) care aruncă
 *        DuplicateEntryException dacă name există deja în listă
 *      - Demonstrează ambele cu try-catch
 *
 *   c) MULTI-CATCH:
 *      - Prinde InvalidAgeException | DuplicateEntryException într-un singur catch
 *
 *   d) CATCH ORDERING:
 *      - Demonstrează că prinderea specifică (InvalidAgeException) trebuie
 *        să fie ÎNAINTE de cea generală (RuntimeException)
 *
 *   e) THROW vs THROWS:
 *      - Creează o metodă cu semnătura: void process(int age) throws InvalidAgeException
 *      - Apeleaz-o din main cu try-catch
 *
 * Output așteptat:
 *
 * === a) Unchecked — NullPointerException ===
 * Prins: Cannot invoke "String.length()" because "s" is null
 * Finally se execută mereu!
 *
 * === b) Custom exceptions ===
 * InvalidAgeException: Vârsta -5 nu este validă (0-150)
 * DuplicateEntryException: 'Ana' există deja în listă
 *
 * === c) Multi-catch ===
 * Excepție prinsă: Vârsta 200 nu este validă (0-150)
 *
 * === d) Catch ordering (specific → general) ===
 * InvalidAgeException prinsă specific: Vârsta -1 nu este validă (0-150)
 *
 * === e) Throw vs throws ===
 * Metoda process() a aruncat: Vârsta 999 nu este validă (0-150)
 */
public class Main {

    private static int riskyMethod() {
        throw new NullPointerException();
    }

    private static int validateAge(int age){
        if (age<0 || age>150){
            throw new InvalidAgeException(age);
        }
        return age;
    }

    private static void addToList(List<String> list, String name){
        for(String nm: list){
            if(nm==name){
                throw new DuplicateEntryException(name);
            }
        }
        list.add(name);
    }

    private static void process(int age) throws InvalidAgeException{
        System.out.println("Let's gooo!");
    }

    public static void main(String[] args) {
        // TODO: implementează pașii de mai sus
        // Hint: creează mai întâi InvalidAgeException.java și DuplicateEntryException.java
        try{
            riskyMethod();
        }
        catch(NullPointerException e){
            System.out.println("Gotcha! Mesaj eroare: "+e.getMessage());
        }
        finally {
            System.out.println("Finally");
        }
        //b
        try{
            int age=-5;
            validateAge(age);
        }
        catch (InvalidAgeException e){
            System.out.println("Mesaj eroare varsta inadmisibila: "+e.getMessage());
        }

        try{
            String[] nume={"Ana", "Maria", "Lavinia"};
            addToList(List.of(nume), "Ana");
        }
        catch (DuplicateEntryException e){
            System.out.println("Mesaj eroare duplicate: "+e.getMessage());
        }
        //c
        try {
            int age=20;
            String[] nume={"Marissa", "Rebecca", "Ares"};
            validateAge(age);
            addToList(List.of(nume), "Ares");
        }
        catch (InvalidAgeException | DuplicateEntryException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        //d- ordering
        try{
            int age=1000;
            validateAge(age);
        }
        catch(InvalidAgeException e){
            System.out.println("Eroare specifica: "+e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("Eroare generala: "+e.getMessage());
        }

        //e
        try{
            int age=-11;
            process(age);
        }
        catch (InvalidAgeException e){
            System.out.println("Eroare arunacata de throws: "+e.getMessage());
        }
    }
}

