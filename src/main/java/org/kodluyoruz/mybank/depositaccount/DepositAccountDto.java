package org.kodluyoruz.mybank.depositaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.customer.Customer;

import javax.validation.constraints.Pattern;
import java.util.Currency;
import java.util.Random;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccountDto {

    private int id;
    @Pattern(regexp = "^TR\\d{7}[0-9A-Z]{17}$")
    private String iban;

    private double accountBalance;

    private String currency;

    private Customer customer;

    public DepositAccount toDepositAccount(){
        return DepositAccount.builder()
                .accountBalance(this.accountBalance)
                .iban(this.iban)
                .currency(this.currency)
                .id(this.id)
                .customer(this.customer)
                .build();
    }
}
