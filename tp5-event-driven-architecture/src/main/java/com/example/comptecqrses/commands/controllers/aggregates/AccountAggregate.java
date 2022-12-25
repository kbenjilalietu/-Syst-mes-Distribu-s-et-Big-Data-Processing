package com.example.comptecqrses.commands.controllers.aggregates;

import com.example.comptecqrses.commonApi.commands.CreditAccountCommand;
import com.example.comptecqrses.commonApi.commands.DebitAccountCommand;
import com.example.comptecqrses.commonApi.enums.AccountStatus;
import com.example.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonApi.events.AccountActivatedEvent;
import com.example.comptecqrses.commonApi.events.AccountCreatedEvent;
import com.example.comptecqrses.commonApi.events.AccountCreditedEvent;
import com.example.comptecqrses.commonApi.events.AccountDebitedEvent;
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

        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event)
    {
        this.status = event.getAccountStatus();
    }

    // La fonction de déscision de  la commande credit
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if (command.getCreditAmount() < 0) {
            throw new RuntimeException("Credit amount cannot be negative");
        }

        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getCreditAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getCreditAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if (command.getDebitAmount() < 0) {
            throw new RuntimeException("Debit amount should not be negative");
        }

        if (this.balance < command.getDebitAmount()) {
            throw new RuntimeException("Balance not sufficient");
        }

        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getDebitAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getDebitAmount();
    }


}
