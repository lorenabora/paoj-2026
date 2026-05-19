package com.pao.laboratory11.exercise3;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // TODO: Manual demo for bonus requirements.
//        System.out.println("TODO: implement laboratory11 exercise3 bonus demo");
        List<Transaction> data = List.of(
                new Transaction( 1,new BigDecimal(1500.00), LocalDate.of(2025,1,5), "Romania","online"),
                new Transaction( 2, new BigDecimal(320.50), LocalDate.of(2026,1,12), "Germania","atm"),
                new Transaction( 3, new BigDecimal(4200.00), LocalDate.of(2027,2, 3), "Franta","online"),
                new Transaction( 4, new BigDecimal(750.75), LocalDate.of(2028,2,18), "Romania","branch"),
                new Transaction( 5, new BigDecimal(5000.00), LocalDate.of( 2029, 5, 21), "Franta", "online"));
        Snapshot snap = data.stream().collect(MyCollector.toSnapshot(3));
        System.out.println("total amount: "+snap.getTotalAmount());
        // interogare 1:
        snap.getTopTransactions().forEach(System.out::println);
        // Interogare 2: total pe țări (desc)
        snap.getCountByCountry().entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey())).forEach(e -> System.out.printf("  %-12s : %d transactii%n", e.getKey(), e.getValue()));
        // Interogare 3: canale ordonate după număr
        snap.getCountByChannel().entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey())).forEach(e -> System.out.printf("  %-10s : %d transactii%n", e.getKey(), e.getValue()));
    }
}
