package org.kodluyoruz.mybank;

import org.kodluyoruz.mybank.creditcard.CreditCard;
import org.kodluyoruz.mybank.creditcard.CreditCardController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditCardControllerTests {
    private CreditCardController creditCardController;
    private CreditCard creditCard;
    @Test
    public void getDebt(){
        Assertions.assertEquals(creditCard.getDebt(),creditCardController.debtInquiry(1));
    }

}
