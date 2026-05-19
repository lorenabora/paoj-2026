package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private final int id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String country;
    private final String channel;

    public Transaction(int id, BigDecimal amount, LocalDate date, String country, String channel) {
        if (amount == null || date == null || country == null || channel == null)
            throw new IllegalArgumentException("No field may be null");
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.country = country;
        this.channel = channel;
    }
    public int getId(){ return id;}
    public BigDecimal getAmount(){ return amount;}
    public LocalDate getDate() { return date;}
    public String getCountry() { return country;}
    public String getChannel() { return channel;}

    @Override
    public String toString() {
        return String.format("Transaction{id=%-3d amount=%10.2f date=%s country=%-10s channel=%s}", id, amount, date, country, channel);
    }
}
