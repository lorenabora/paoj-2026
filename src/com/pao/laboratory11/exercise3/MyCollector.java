package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;

public class MyCollector {
    private MyCollector() {}

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        if (topN < 0) throw new IllegalArgumentException("topN trb sa fie >= 0");
        class Agg {
            final Map<String, Long> countByCountry = new HashMap<>();
            final Map<String, Long> countByChannel = new HashMap<>();
            BigDecimal totalAmount = BigDecimal.ZERO;
            final List<Transaction> all = new ArrayList<>();

            void add(Transaction tx) {
                String c = tx.getCountry();
                String ch = tx.getChannel();
                BigDecimal amt = tx.getAmount();
                countByCountry.merge(c,1L,Long::sum);
                countByChannel.merge(ch, 1L, Long::sum);
                totalAmount = totalAmount.add(amt);
                all.add(tx);
            }

            Agg merge(Agg other) {
                other.countByCountry.forEach((k, v) -> countByCountry.merge(k, v, Long::sum));
                other.countByChannel.forEach((k, v) -> countByChannel.merge(k, v, Long::sum));
                totalAmount = totalAmount.add(other.totalAmount);
                all.addAll(other.all);
                return this;
            }
            Snapshot finish() {
                Comparator<Transaction> byAmountDescThenIdAsc = Comparator.comparing(Transaction::getAmount).reversed().thenComparingInt(Transaction::getId);
                List<Transaction> top = all.stream().sorted(byAmountDescThenIdAsc).limit(topN).toList();
                return new Snapshot(countByCountry, countByChannel, totalAmount, top);
            }
        }
        return Collector.of(
                Agg::new,
                Agg::add,
                Agg::merge,
                Agg::finish,
                Collector.Characteristics.UNORDERED
        );
    }
}
