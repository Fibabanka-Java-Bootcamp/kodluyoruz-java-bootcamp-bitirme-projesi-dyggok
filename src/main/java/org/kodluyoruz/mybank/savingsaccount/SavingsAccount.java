package org.kodluyoruz.mybank.savingsaccount;

import org.kodluyoruz.mybank.customer.Customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kodluyoruz.mybank.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ComponentScan
@Entity
@Table(name = "savingsaccount")
public class SavingsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String iban;

    private double accountBalance;

    private String currency;

    @OneToOne(mappedBy = "savingsAccount")
    private Customer customer;

    @OneToMany(mappedBy = "savingsAccount")
    private List<Transfer> transfers;

    public SavingsAccountDto toSavingsAccountDto(){
        return SavingsAccountDto.builder()
                .accountBalance(this.accountBalance)
                .currency(this.currency)
                .id(this.id)
                .iban(this.iban)
                .build();
    }
}
