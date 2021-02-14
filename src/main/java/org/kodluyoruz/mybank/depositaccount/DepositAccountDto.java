package org.kodluyoruz.mybank.depositaccount;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Currency;
import java.util.Random;
import java.util.UUID;

@Builder
@Data
public class DepositAccountDto {

    private int id;
    @Pattern(regexp = "^TR\\d{7}[0-9A-Z]{17}$")
    private String iban;

    private double accountBalance;

    private String currency;

    public DepositAccount toDepositAccount(){
        return DepositAccount.builder()
                .accountBalance(this.accountBalance)
                .iban(this.iban)
                .currency(this.currency)
                .id(this.id)
                .build();
    }
}
