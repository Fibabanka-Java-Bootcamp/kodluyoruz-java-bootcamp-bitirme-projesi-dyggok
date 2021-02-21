package org.kodluyoruz.mybank.prepaidcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ComponentScan
@Entity
@Table(name = "prepaid_card")
public class PrepaidCard {
    @Id
    @GeneratedValue
    private int id;

    private String card_no;

    private String cvv;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depositaccount_id",referencedColumnName = "id")
    private DepositAccount depositAccount;

    public PrepaidCardDto toPrepaidDto(){
        return PrepaidCardDto.builder()
                .id(this.id)
                .card_no(this.card_no)
                .cvv(this.cvv)
                .depositAccount(this.depositAccount)
                .build();
    }

}

