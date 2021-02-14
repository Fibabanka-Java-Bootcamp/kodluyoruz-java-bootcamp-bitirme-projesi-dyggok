package org.kodluyoruz.mybank.prepaidcard;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class PrepaidCardDto {
    private int id;
    @Pattern(regexp = "[0-9\\\\s]{16}")
    private long card_no;
    @Pattern(regexp = "[0-9\\\\s]{3}")
    private int cvv;

    public PrepaidCard toCreditCards() {
        return PrepaidCard.builder()
                .id(this.id)
                .card_no(this.card_no)
                .cvv(this.cvv)
                .build();
    }

}
