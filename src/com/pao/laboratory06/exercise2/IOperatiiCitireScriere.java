package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public interface IOperatiiCitireScriere {
//    void citeste(Scanner in) — citește datele obiectului din input
    void citeste(Scanner in);
//    void afiseaza() — afișează datele obiectului în formatul cerut
    void afiseaza();
//    String tipContract() — returnează tipul contractului
    String tipContract();
//    default boolean areBonus() { return false; } — doar pentru CIM, dacă are bonus, venitul net anual crește cu 10%
    default boolean areBonus(){ return false;}
}
