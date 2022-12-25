package com.example.comptecqrses.query.Service;

import com.example.comptecqrses.commonApi.Queries.GetAccountQuery;
import com.example.comptecqrses.commonApi.Queries.GetAllAccountsQuery;
import com.example.comptecqrses.commonApi.enums.AccountStatus;
import com.example.comptecqrses.commonApi.enums.OperationType;
import com.example.comptecqrses.commonApi.events.AccountActivatedEvent;
import com.example.comptecqrses.commonApi.events.AccountCreatedEvent;
import com.example.comptecqrses.commonApi.events.AccountCreditedEvent;
import com.example.comptecqrses.commonApi.events.AccountDebitedEvent;
import com.example.comptecqrses.query.Entity.Account;
import com.example.comptecqrses.query.Entity.Operation;
import com.example.comptecqrses.query.Repository.AccountRepository;
import com.example.comptecqrses.query.Repository.OperationAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service @AllArgsConstructor
@Slf4j

public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationAccountRepository operationAccountRepository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("****************************");
        log.info("AccountCreatedEvent received");

        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getAccountBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(AccountStatus.CREATED);

        Operation operation = new Operation();
        operation.setDate(new Date());
        operation.setMontant(event.getAccountBalance());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);

        operationAccountRepository.save(operation);
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("****************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getAccountStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("****************************");
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setMontant(event.getCreditAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationAccountRepository.save(operation);
        account.setBalance(account.getBalance() + event.getCreditAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("****************************");
        log.info("AccountDebitedEvent received");

        Account account = accountRepository.findById(event.getId()).get();

        Operation operation = new Operation();
        operation.setMontant(event.getDebitAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationAccountRepository.save(operation);
        account.setBalance(account.getBalance() - event.getDebitAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountQuery query) {
        return accountRepository.findById(query.getId()).get();
    }
}
