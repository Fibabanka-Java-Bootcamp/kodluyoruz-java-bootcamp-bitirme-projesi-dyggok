package org.kodluyoruz.mybank;

import static org.junit.Assert.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.depositaccount.DepositAccountController;
import org.kodluyoruz.mybank.depositaccount.DepositAccountDto;
import org.kodluyoruz.mybank.prepaidcard.PrepaidCard;
import org.kodluyoruz.mybank.transfer.Transfer;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Stack;

public class DepositAccountControllerTests {
    private DepositAccount depositAccount;
    private DepositAccountController depositAccountController;
    private Customer customer;
    private DepositAccountDto depositAccountDto =
            new DepositAccountDto(1,"TR080006237748378319299917",
                    200,"TRY",customer);

    @Test
    public void createDepoAcc(){
        Assertions.assertAll("depositAccountController",
                ()-> assertEquals(depositAccountDto.getId(),depositAccountController.create(depositAccountDto).getId()),
                ()-> assertEquals(depositAccountDto.getIban(),depositAccountController.create(depositAccountDto).getIban()),
                ()-> assertEquals(depositAccountDto.getAccountBalance(), depositAccountController.create(depositAccountDto).getAccountBalance(),0.001),
                ()-> assertEquals(depositAccountDto.getCurrency(),depositAccountController.create(depositAccountDto).getCurrency())
        );
    }

    @Nested
    @DisplayName("when an account")
    class deleteAccount {
        @BeforeAll
        void createAcc() {
            depositAccount = new DepositAccount();
        }

        @Test
        @DisplayName("is null")
        void throwException() {
            assertThrows(NullPointerException.class, () -> depositAccount.getId());
        }

        @Nested
        @DisplayName("is not null but balance is zero")
        class deleteAcc {
            @BeforeAll
            void setAcc() {
                depositAccount.setId(1);
                depositAccount.setAccountBalance(0);
            }

            @Test
            void deleteAll() {
                assertEquals("deleted", depositAccountController.deleteDepositAccount(depositAccount.getId()));
            }

            @Nested
            @DisplayName("is not null and balance is greater than zero")
            class notDeleteAcc {
                @BeforeAll
                void setAcc() {
                    depositAccount.setId(1);
                    depositAccount.setAccountBalance(100);
                }

                @Test
                void notDelete() {
                    assertEquals("The account cannot be deleted" + depositAccount.getId(),
                            depositAccountController.deleteDepositAccount(depositAccount.getId()));
                }
            }
        }

    }
}
