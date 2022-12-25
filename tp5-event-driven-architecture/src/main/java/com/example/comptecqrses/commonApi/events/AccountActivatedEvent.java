package com.example.comptecqrses.commonApi.events;


import com.example.comptecqrses.commonApi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String> {
    private AccountStatus accountStatus;

    public AccountActivatedEvent(String id, AccountStatus accountStatus) {
        super(id);
        this.accountStatus = accountStatus;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}
