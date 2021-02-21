package org.kodluyoruz.mybank.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Currency;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    private int id;
    private LocalDate datetime;
    private String trans_type;
    private double amount;

    public Transfer toTransfer(){
        return Transfer.builder()
                .id(this.id)
                .amount(this.amount)
                .datetime(this.datetime)
                .trans_type(this.trans_type)
                .build();
    }
}

