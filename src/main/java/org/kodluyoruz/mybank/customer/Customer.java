package org.kodluyoruz.mybank.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private SavingsAccount savingsAccount;

    @JsonIgnore
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private DepositAccount depositAccount;

    @JsonIgnore
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private CreditCard creditCard;

    public CustomerDto toCustomerDto(){
        return CustomerDto.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .phone(this.phone)
                .email(this.email)
                .build();
    }
}
