package org.kodluyoruz.mybank.creditcard;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class CreditCardDto {
    private int id;
    @Pattern(regexp = "[0-9\\\\s]{16}")
    private String card_no;
    @Pattern(regexp = "[0-9\\\\s]{3}")
    private String cvv;
    @Min(value = -1000,message = "do not exceed the limit")
    private double debt;

    public CreditCard toCreditCards(){
        return CreditCard.builder()
                .id(this.id)
                .card_no(this.card_no)
                .cvv(this.cvv)
                .debt(this.debt)
                .build();
    }

}
