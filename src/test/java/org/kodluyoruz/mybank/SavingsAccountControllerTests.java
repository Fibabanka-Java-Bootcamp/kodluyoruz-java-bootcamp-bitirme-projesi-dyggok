package org.kodluyoruz.mybank;

import org.junit.jupiter.api.*;

import static org.junit.Assert.*;

import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountController;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountDto;

public class SavingsAccountControllerTests {
    private SavingsAccount savingsAccount;
    private SavingsAccountController savingsAccountController;
    private Customer customer;
    private SavingsAccountDto savingsAccountDto =
            new SavingsAccountDto(1,"TR080006237748378319299917",200,"TRY",customer);

    @Test
    public void createAcc(){
        Assertions.assertAll("savingsAccountController",
                () -> assertEquals(savingsAccountDto.getId(),savingsAccountController.create(savingsAccountDto).getId()),
                () -> assertEquals(savingsAccountDto.getIban(),savingsAccountController.create(savingsAccountDto).getIban()),
                () -> assertEquals(savingsAccountDto.getAccountBalance(), savingsAccountController.create(savingsAccountDto).getAccountBalance(),0.001),
                () -> assertEquals(savingsAccountDto.getCurrency(),savingsAccountController.create(savingsAccountDto).getCurrency())
                );
    }

    @Nested
    @DisplayName("when an account")
    class deleteAccount{
        @BeforeAll
        void createAcc(){
            savingsAccount = new SavingsAccount();
        }
        @Test
        @DisplayName("is null")
        void throwsException(){
            assertThrows(NullPointerException.class,
                    ()->savingsAccountController.deleteSavingsAccount(savingsAccount.getId()));
        }
        @Nested
        @DisplayName("is not null but balance is zero")
        class deleteAll{
            @BeforeAll
            void setUp(){
                savingsAccount.setId(1);
                savingsAccount.setAccountBalance(0);
            }
            @Test
            void delAcc(){
                assertEquals("deleted"+Boolean.TRUE,
                        savingsAccountController.deleteSavingsAccount(savingsAccount.getId()));
            }
            @Nested
            @DisplayName("is not null and balance is greater than zero")
            class notDeleteAcc{
                @BeforeAll
                void setUp(){
                    savingsAccount.setId(1);
                    savingsAccount.setAccountBalance(100);
                }
                @Test
                void notDeleteAcc(){
                    assertEquals( "The account cannot be deleted : ",savingsAccount.getId(),
                            savingsAccountController.deleteSavingsAccount(savingsAccount.getId()));
                }
            }
        }
    }

}
