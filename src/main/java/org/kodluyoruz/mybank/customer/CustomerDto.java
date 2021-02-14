package org.kodluyoruz.mybank.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private int id;
    @NotEmpty(message = "firstname cannot be blank")
    private String firstname;
    @NotEmpty(message = "lastname cannot be blank")
    private String lastname;
    @Pattern(regexp ="[0-9\\s]{12}")
    private String phone;
    @Email(message = "bad mail")
    private String email;

    public Customer toCustomer(){
        return Customer.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .phone(this.phone)
                .email(this.email)
                .build();

    }
}

