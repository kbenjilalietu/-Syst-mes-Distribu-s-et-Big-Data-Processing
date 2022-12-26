package com.example.comptecqrses.query.Repository;

import com.example.comptecqrses.query.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}

