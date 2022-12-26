package com.example.comptecqrses.commonApi.events;

import lombok.Getter;

public class AccountCreditedEvent extends BaseEvent<String>
{
    @Getter private double creditAmount;
    @Getter private String currency;

    public AccountCreditedEvent(String id, double creditAmount, String currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }

}
