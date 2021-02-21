package org.kodluyoruz.mybank.savingsaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.customer.Customer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Pattern;
import java.util.Currency;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountDto {
    private int id;
    @Pattern(regexp = "^TR\\d{7}[0-9A-Z]{17}$")
    private String iban;
    private double accountBalance;
    private String currency;
    private Customer customer;

    public SavingsAccount toSavingsAccount(){
        return SavingsAccount.builder()
                .accountBalance(this.accountBalance)
                .currency(this.currency)
                .id(this.id)
                .iban(this.iban)
                .customer(this.customer)
                .build();
    }

}

