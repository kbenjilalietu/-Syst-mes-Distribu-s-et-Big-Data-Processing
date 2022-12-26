package com.example.comptecqrses.commands.controllers;

import com.example.comptecqrses.commonApi.commands.CreditAccountCommand;
import com.example.comptecqrses.commonApi.commands.DebitAccountCommand;
import com.example.comptecqrses.commonApi.dtos.CreateAccountRequestDTO;
import com.example.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonApi.dtos.CreditAccountRequestDTO;
import com.example.comptecqrses.commonApi.dtos.DebitAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController
{
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request)
    {
        // chaque fois il y a une commande créer on va la mettre
        // va nous retouner l'id de commande
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception)
    {
        ResponseEntity<String> entity = new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    // afficher Event Store où on trouve les info des events
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId)
    {
        return  eventStore.readEvents(accountId).asStream();
    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request)
    {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getCreditAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(request.getAccountId(),
                request.getDebitAmount(), request.getCurrency()));
        return commandResponse;
    }

}
