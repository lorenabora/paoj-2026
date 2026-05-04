package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // Folosește LinkedList<Tranzactie> ca structură internă.
        // Citește comenzi din stdin până la EOF:
        //
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        LinkedList<Tranzactie> coada = new LinkedList<>();
        while (scanner.hasNext()) {
            String comanda = scanner.next();
            switch (comanda) {
                case "ENQUEUE":
                    int eqId = scanner.nextInt();
                    double eqSuma = scanner.nextDouble();
                    String eqData = scanner.next();
                    TipTranzactie eqTip = TipTranzactie.valueOf(scanner.next());
                    coada.addLast(new Tranzactie(eqId, eqSuma, eqData, eqTip));
                    break;
                case "DEQUEUE":
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Procesat: " + coada.removeFirst());
                    }
                    break;
                case "PUSH":
                    int pId = scanner.nextInt();
                    double pSuma = scanner.nextDouble();
                    String pData = scanner.next();
                    TipTranzactie pTip = TipTranzactie.valueOf(scanner.next());
                    coada.addFirst(new Tranzactie(pId, pSuma, pData, pTip));
                    break;
                case "POP":
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Extras: " + coada.removeFirst());
                    }
                    break;
                case "REMOVE_DEBIT":
                    int nrDebit = 0;
                    Iterator<Tranzactie> itDebit = coada.iterator();
                    while (itDebit.hasNext()) {
                        if (itDebit.next().getTip() == TipTranzactie.DEBIT) {
                            itDebit.remove();
                            nrDebit++;
                        }
                    }
                    System.out.println("Eliminat " + nrDebit + " tranzactii DEBIT.");
                    break;
                case "REMOVE_BELOW":
                    double threshold = scanner.nextDouble();
                    int nrBelow = 0;
                    Iterator<Tranzactie> itBelow = coada.iterator();
                    while (itBelow.hasNext()) {
                        if (itBelow.next().getSuma() < threshold) {
                            itBelow.remove();
                            nrBelow++;
                        }
                    }
                    System.out.printf(Locale.US, "Eliminat %d tranzactii sub %.2f RON.\n", nrBelow, threshold);
                    break;
                case "PRINT":
                    for (Tranzactie t : coada) {
                        System.out.println(t);
                    }
                    break;
                case "SIZE":
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
            }
        }
        scanner.close();
//        System.out.println("TODO: implementează exercițiul 1");
    }
}
