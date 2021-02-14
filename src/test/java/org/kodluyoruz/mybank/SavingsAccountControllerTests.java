package org.kodluyoruz.mybank;

import org.kodluyoruz.mybank.savingsaccount.SavingsAccountController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingsAccountControllerTests {
    private SavingsAccountController savingsAccountController;

    @Test
    public void deleteAccount(){
        Assertions.assertEquals("deleted",savingsAccountController.deleteSavingsAccount(1));

    }

}
