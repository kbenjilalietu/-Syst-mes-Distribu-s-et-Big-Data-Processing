package com.example.comptecqrses.commonApi.events;

import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String> {
    @Getter private double accountBalance;
    @Getter private String currency;

    public AccountCreatedEvent(String id, double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
