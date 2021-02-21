package org.kodluyoruz.mybank;

import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.prepaidcard.PrepaidCardController;
import org.kodluyoruz.mybank.prepaidcard.PrepaidCardDto;

public class PrepaidCardControllerTests {
    private DepositAccount depositAccount;
    private PrepaidCardDto prepaidCardDto =
            new PrepaidCardDto(1,"4859625471563284","618",depositAccount);
    private PrepaidCardController prepaidCardController;

    @Test
    public void createCard(){
        Assertions.assertAll("prepaidCardController",
                () -> assertEquals(prepaidCardDto.getId(),prepaidCardController.create(prepaidCardDto).getId()),
                () -> assertEquals(prepaidCardDto.getCard_no(),prepaidCardController.create(prepaidCardDto).getCard_no()),
                () -> assertEquals(prepaidCardDto.getCvv(),prepaidCardController.create(prepaidCardDto).getCvv())
                );
    }
}
