package org.kodluyoruz.mybank.prepaidcard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrepaidCardDto {
    private int id;
    @Pattern(regexp = "[0-9\\\\s]{16}")
    private String card_no;
    @Pattern(regexp = "[0-9\\\\s]{3}")
    private String cvv;
    private DepositAccount depositAccount;

    public PrepaidCard toCreditCards() {
        return PrepaidCard.builder()
                .id(this.id)
                .card_no(this.card_no)
                .cvv(this.cvv)
                .depositAccount(this.depositAccount)
                .build();
    }

}
