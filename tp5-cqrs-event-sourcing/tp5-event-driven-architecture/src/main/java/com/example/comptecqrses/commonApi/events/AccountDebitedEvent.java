package com.example.comptecqrses.commonApi.events;

import lombok.Getter;

public class AccountDebitedEvent extends BaseEvent<String> {
    @Getter private double debitAmount;
    @Getter private String currency;

    public AccountDebitedEvent(String id, double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}
