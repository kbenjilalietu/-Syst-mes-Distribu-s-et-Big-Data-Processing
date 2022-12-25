package com.example.comptecqrses.commands.controllers;

import com.example.comptecqrses.commonApi.dtos.CreateAccountRequestDTO;
import com.example.comptecqrses.commonApi.commands.CreateAccountCommand;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController
{
    private CommandGateway commandGateway;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(CreateAccountRequestDTO request)
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

}
