package org.kodluyoruz.mybank;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;

import org.kodluyoruz.mybank.creditcard.CreditCard;
import org.kodluyoruz.mybank.creditcard.CreditCardController;
import org.kodluyoruz.mybank.creditcard.CreditCardDto;
import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.springframework.http.HttpStatus;

public class CreditCardControllerTests {
    private CreditCard creditCard;
    private SavingsAccount savingsAccount;
    private DepositAccount depositAccount;
    private Customer customer;
    private CreditCardController creditCardController;
    private CreditCardDto creditCardDto = new CreditCardDto(1,"5964871254698743","618",0,customer);

    @Test
    public void createCard(){
        Assertions.assertAll("creditCardController",
                ()-> assertEquals(creditCardDto.getId(),creditCardController.create(creditCardDto).getId()),
                ()-> assertEquals(creditCardDto.getCard_no(),creditCardController.create(creditCardDto).getCard_no()),
                () -> assertEquals(creditCardDto.getCvv(),creditCardController.create(creditCardDto).getCvv())
        );
    }

    @Nested
    @DisplayName("when a credit card")
    class debtCreditCardwithAcc{
        @BeforeAll
        void createCard(){
            creditCard = new CreditCard();
            depositAccount = new DepositAccount();
            savingsAccount = new SavingsAccount();
        }
        @Test
        @DisplayName("is null")
        void throwsException(){
            assertThrows(NullPointerException.class, () ->
                    creditCardController.debtOnAccount(creditCard.getId(), depositAccount.getIban(),0));
        }
        @Nested
        @DisplayName("is not null and savingsAccount is not null")
        class debtWithSavAcc {
            @BeforeEach
            void setUp() {
                creditCard.setId(1);
                creditCard.setDebt(-100);
                savingsAccount.setId(1);
                savingsAccount.setIban("TR080006237748378319299917");
                savingsAccount.setAccountBalance(100);
            }

            @Test
            @DisplayName("money is greater than balance")
            void controlBalance() {
                assertEquals("Insufficient balance",
                        creditCardController.debtOnAccount(creditCard.getId(), savingsAccount.getIban(), 200));
            }
            @Test
            @DisplayName("money is not greater than balance")
            void debtAll(){
                assertEquals(true,
                        creditCardController.debtOnAccount(creditCard.getId(),savingsAccount.getIban(),50));
            }
        }
        @Nested
        @DisplayName("is not null and depositAccount is not null")
        class debtWithDepAcc{
            @BeforeEach
            void setUp(){
                creditCard.setId(1);
                creditCard.setDebt(-100);
                depositAccount.setId(1);
                depositAccount.setIban("TR080006237748378319299917");
                depositAccount.setAccountBalance(100);
            }
            @Test
            @DisplayName("money is greater than balance")
            void controlBalance(){
                assertEquals("Insufficient balance",
                        creditCardController.debtOnAccount(creditCard.getId(), depositAccount.getIban(), 200));
            }
            @Test
            @DisplayName("money is not greater than balance")
            void debtAll(){

                assertEquals(true,
                        creditCardController.debtOnAccount(creditCard.getId(), depositAccount.getIban(), 50));
            }
        }

    }

    @Nested
    @DisplayName("when a credit card")
    class debtCreditCardwithAtm{
        @BeforeAll
        void createCrediCard(){
            creditCard = new CreditCard();
        }
        @Test
        @DisplayName("is null")
        void throwsException(){
            assertThrows(NullPointerException.class,
                    () -> creditCardController.debtViaAtm(creditCard.getId(),0));
        }
        @Nested
        @DisplayName("is not null")
        class debtAllwithAtm{
            @BeforeAll
            void setUp(){
                creditCard.setId(1);
                creditCard.setDebt(-100);
            }
            @Test
            void debtWithAtm(){
                assertEquals(Boolean.TRUE,creditCardController.debtViaAtm(creditCard.getId(),100));
            }
        }
    }

    @Nested
    @DisplayName("when a credit card")
    class getDebt{
        @BeforeAll
        void createCard(){
            creditCard = new CreditCard();
        }
        @Test
        @DisplayName("is null")
        void throwsException(){
            assertThrows(NullPointerException.class,()-> creditCardController.debtInquiry(creditCard.getId()));
        }
        @Nested
        @DisplayName("is not null so get the debt of the credit card")
        class getDebtofCard{
            @BeforeAll
            void setUp(){
                creditCard.setId(1);
                creditCard.setDebt(-100);
            }
            @Test
            void getDebt(){
                assertEquals(creditCard.getDebt(),creditCardController.debtInquiry(creditCard.getId()),0.0);
            }
        }
    }

    @Nested
    @DisplayName("when shopping with credit card")
    class shoppingWithCard{
        @BeforeAll
        void createCard(){
            creditCard = new CreditCard();
        }
        @Test
        @DisplayName("and the credit card is null")
        void throwsException(){
            assertThrows(NullPointerException.class,()-> creditCardController.shopping(creditCard.getId(),0));
        }
        @Nested
        @DisplayName("and the credit card is not null")
        class shopping{
            @BeforeAll
            void setUp(){
                creditCard.setId(1);
            }
            @Test
            void drawal(){
                assertEquals(Boolean.TRUE,creditCardController.shopping(creditCard.getId(),50));
            }
        }
    }
}
