package com.example.comptecqrses.aggregates;

import com.example.comptecqrses.commonApi.enums.AccountStatus;
import com.example.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonApi.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

// la classe où on va executer la logique métier
@Aggregate
public class AccountAggregate
{
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate()
    {
        //required by AXON
    }

    // La fonction de décision
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand)
    {
        if(createAccountCommand.getInitialBalance()<0) throw new RuntimeException("Impossible ...");
        //OK
        // Créer un evenement et stocker dans lui createAccountCommand
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency()
        ));
    }
    // La fonction d'évolution
    @EventSourcingHandler
    public void on(AccountCreatedEvent event)
    {
        this.accountId = event.getId();
        this.balance = event.getAccountBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
    }
}
