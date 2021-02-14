package org.kodluyoruz.mybank.depositaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.prepaidcard.PrepaidCard;
import org.kodluyoruz.mybank.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposit_account")
public class DepositAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String iban;
    private double accountBalance;

    private String currency;

    @OneToOne(mappedBy = "depositAccount")
    private Customer customer;

    @OneToOne(mappedBy = "depositAccount")
    private PrepaidCard prepaidCard;

    @OneToMany(mappedBy = "depositAccount")
    List<Transfer> transfers;

    public DepositAccountDto toDepositAccountDto(){
        return DepositAccountDto.builder()
                .accountBalance(this.accountBalance)
                .iban(this.iban)
                .currency(this.currency)
                .id(this.id)
                .build();
    }

}

