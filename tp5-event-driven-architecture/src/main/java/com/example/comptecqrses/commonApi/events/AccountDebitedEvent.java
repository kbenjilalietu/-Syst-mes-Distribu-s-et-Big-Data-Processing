package com.example.comptecqrses.commonApi.events;

public class AccountDebitedEvent extends BaseEvent<String> {
    private double debitAmount;
    private String currency;

    public AccountDebitedEvent(String id, double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public String getCurrency() {
        return currency;
    }
}
