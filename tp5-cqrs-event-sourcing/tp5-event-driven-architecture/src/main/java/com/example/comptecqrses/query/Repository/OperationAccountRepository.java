package com.example.comptecqrses.query.Repository;


import com.example.comptecqrses.query.Entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationAccountRepository extends JpaRepository<Operation, String> {

}

