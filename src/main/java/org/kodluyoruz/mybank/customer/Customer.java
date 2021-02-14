package org.kodluyoruz.mybank.customer;

import org.kodluyoruz.mybank.creditcard.CreditCard;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;

    @OneToOne
    @JoinColumn(name = "savingsaccount_id",referencedColumnName = "id")
    private SavingsAccount savingsAccount;

    @OneToOne
    @JoinColumn(name = "depositaccount_id",referencedColumnName = "id")
    private DepositAccount depositAccount;

    @OneToOne(mappedBy = "customer")
    private CreditCard creditCards;

    public CustomerDto toCustomerDto(){
        return CustomerDto.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .phone(this.phone)
                .build();
    }
}
