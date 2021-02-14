package org.kodluyoruz.mybank;

import org.kodluyoruz.mybank.customer.CustomerController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerControllerTests {
    private CustomerController customerController;

    @Test
    void deleteCustomer(){
        Assertions.
                assertEquals("deleted",customerController.deleteCustomer(1));
    }
    @Test
    void getCustomer(){
        Assertions.
                assertEquals("firstname lastname",customerController.list("firstname","lastname"));
    }
}
