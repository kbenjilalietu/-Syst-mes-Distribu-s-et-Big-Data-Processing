package com.example.comptecqrses.commonApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitAccountRequestDTO {
    private String accountId;
    private double debitAmount;
    private String currency;

}
