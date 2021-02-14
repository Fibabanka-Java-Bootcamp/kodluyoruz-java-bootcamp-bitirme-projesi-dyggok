package org.kodluyoruz.mybank;

import org.kodluyoruz.mybank.depositaccount.DepositAccountController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DepositAccountControllerTests {
    private DepositAccountController depositAccountController;

    @Test
    public void deleteAccount(){
        Assertions.assertEquals("deleted",depositAccountController.deleteDepositAccount(1));
    }
}
