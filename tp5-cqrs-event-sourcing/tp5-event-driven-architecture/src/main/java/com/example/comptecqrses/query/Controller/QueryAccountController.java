package com.example.comptecqrses.query.Controller;

import com.example.comptecqrses.commonApi.Queries.GetAccountQuery;
import com.example.comptecqrses.commonApi.Queries.GetAllAccountsQuery;
import com.example.comptecqrses.query.Entity.Account;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/query/accounts")
@AllArgsConstructor
public class QueryAccountController
{
    private QueryGateway queryGateway;


    @GetMapping(path = "/allAccounts")
    public List<Account> getAccounts() {
        return queryGateway.query(
                new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class)).join();
    }
    
    @GetMapping(path="/getAccount/{id}")
    public Account getAccount(@PathVariable String id) {
        return queryGateway.query(new GetAccountQuery(id),
                ResponseTypes.instanceOf(Account.class)).join();
    }
}
