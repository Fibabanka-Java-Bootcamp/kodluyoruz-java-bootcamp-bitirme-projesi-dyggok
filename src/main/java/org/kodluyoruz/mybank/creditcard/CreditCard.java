package org.kodluyoruz.mybank.creditcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kodluyoruz.mybank.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creditcard")
public class CreditCard {
    @Id
    @GeneratedValue
    private int id;

    private String card_no;

    private String cvv;

    private double debt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    public CreditCardDto toCreditCardDto(){
        return CreditCardDto.builder()
                .id(this.id)
                .card_no(this.card_no)
                .cvv(this.cvv)
                .debt(this.debt)
                .customer(this.customer)
                .build();
    }

}

