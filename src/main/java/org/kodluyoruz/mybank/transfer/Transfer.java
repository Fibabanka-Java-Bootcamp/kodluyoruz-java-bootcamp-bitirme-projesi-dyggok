package org.kodluyoruz.mybank.transfer;

import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Currency;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ComponentScan
@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue
    private int id;

    private LocalDate datetime;

    private String trans_type;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "depositaccount_id",referencedColumnName = "id")
    private DepositAccount depositAccount;

    @ManyToOne
    @JoinColumn(name = "savingsaccount_id",referencedColumnName = "id")
    private SavingsAccount savingsAccount;

    public TransferDto toTransferDto(){
        return TransferDto.builder()
                .id(this.id)
                .trans_type(this.trans_type)
                .amount(this.amount)
                .datetime(this.datetime)
                .build();
    }
}

