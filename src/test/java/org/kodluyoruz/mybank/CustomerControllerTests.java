package org.kodluyoruz.mybank;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.kodluyoruz.mybank.creditcard.CreditCard;
import org.kodluyoruz.mybank.customer.CustomerController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kodluyoruz.mybank.customer.CustomerDto;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.springframework.boot.test.context.SpringBootTest;

public class CustomerControllerTests {
    private CustomerController customerController;
    private DepositAccount depositAccount;
    private SavingsAccount savingsAccount;
    private CreditCard creditCard;
    private final CustomerDto customerDto = new CustomerDto(1,"Jane","Doe",
            "095417692624","janedoe@gmail.com");

    @Test
    public void createCustomer(){
        Assertions.assertAll("customerController",
                ()-> assertEquals(customerDto.getId(),customerController.create(customerDto).getId()),
                ()-> assertEquals(customerDto.getFirstname(),customerController.create(customerDto).getFirstname()),
                ()-> assertEquals(customerDto.getLastname(),customerController.create(customerDto).getLastname()),
                ()-> assertEquals(customerDto.getPhone(),customerController.create(customerDto).getPhone()),
                ()-> assertEquals(customerDto.getEmail(),customerController.create(customerDto).getEmail())
        );
        assertNotNull(customerController.create(customerDto).getFirstname());
        assertNotNull(customerController.create(customerDto).getLastname());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jane","Doe"})
    public void listCustomer(String firstname,String lastname){
        Assertions.assertAll("customerController",
                ()-> assertEquals(firstname,customerController.list(firstname,lastname).get(0)),
                ()-> assertEquals(lastname,customerController.list(firstname,lastname).get(1))
        );
    }
    @ParameterizedTest
    @ValueSource(ints = {1})
    public void deleteCustomer(int id){
        assertEquals("deleted",customerController.deleteCustomer(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void notDeleteCustomer(int id){
        assertEquals("The Customer cannot be deleted",customerController.deleteCustomer(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void nullPointerException(int id){
        assertThrows(NullPointerException.class,()-> customerController.deleteCustomer(id));
    }
}
