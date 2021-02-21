package org.kodluyoruz.mybank;

import org.junit.Before;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.kodluyoruz.mybank.transfer.TransferController;
import org.kodluyoruz.mybank.transfer.TransferDto;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public class TransferControllerTests {
    private DepositAccount depositAccount;
    private SavingsAccount savingsAccount;
    private DepositAccount depositAccount2;
    private TransferController transferController;
    private TransferDto transferDto = new TransferDto(1, LocalDate.now(),"TRY",200);

    @Nested
    @DisplayName("when be transferred between accounts")
    class transferWithAcc{
        @BeforeAll
        void createAcc(){
            depositAccount = new DepositAccount();
            savingsAccount = new SavingsAccount();
        }
        @Nested
        @DisplayName("and transfer from savingAcc, transfer to depoAcc")
        class transferFromSavAcc{
            @BeforeEach
            void setUp(){
                savingsAccount.setId(1);
                savingsAccount.setIban("TR240006219445741737393617");
                depositAccount.setId(1);
                depositAccount.setIban("TR240006219445741737393627");
                depositAccount.setAccountBalance(50);
            }
            @Test
            @DisplayName("transfer amount is greater than account balance")
            void throwsException(){
                savingsAccount.setAccountBalance(50);
                assertThrows(ResponseStatusException.class,
                        ()-> transferController.create(transferDto,savingsAccount.getIban(),depositAccount.getIban()));

            }
            @Test
            @DisplayName("transfer amount is not greater than account balance")
            void postTransferwithAcc(){
                savingsAccount.setAccountBalance(300);
                assertEquals(null,
                        transferController.create(transferDto,savingsAccount.getIban(),depositAccount.getIban()));
            }
        }
        @Nested
        @DisplayName("and transfer from depoAcc, transfer to savAcc")
        class transferFromDepoAcc{
            @BeforeEach
            void setUp(){
                savingsAccount.setId(1);
                savingsAccount.setIban("TR240006219445741737393617");
                depositAccount.setId(1);
                depositAccount.setIban("TR240006219445741737393627");
                depositAccount.setAccountBalance(50);
            }
            @Test
            @DisplayName("transfer amount is greater than account balance")
            void throwsException(){
                depositAccount.setAccountBalance(50);
                assertThrows(ResponseStatusException.class,
                        ()-> transferController.create(transferDto,depositAccount.getIban(),savingsAccount.getIban()));

            }
            @Test
            @DisplayName("transfer amount is not greater than account balance")
            void postTransferwithAcc(){
                depositAccount.setAccountBalance(300);
                assertEquals(null,
                        transferController.create(transferDto,depositAccount.getIban(),savingsAccount.getIban()));
            }
        }
    }

    @Nested
    @DisplayName("when be transferred to someone else")
    class transferWithSomeoneElse{
        @BeforeAll
        void createAcc(){
            depositAccount = new DepositAccount();
            savingsAccount = new SavingsAccount();
            depositAccount2 = new DepositAccount();
        }

        @Nested
        @DisplayName("transfer from depoAcc to savAcc")
        class transferToSavAcctoSomeoneElse {
            @BeforeEach
            void setUp(){
                depositAccount.setId(1);
                depositAccount.setIban("TR240006219445741737393617");
                savingsAccount.setId(1);
                savingsAccount.setIban("TR240006219445741737393627");
            }
            @Test
            @DisplayName("and transfer amount is greater than account balance")
            void throwsException(){
                assertThrows(ResponseStatusException.class,
                        ()-> transferController.createtoSomeone(transferDto,depositAccount.getIban(), savingsAccount.getIban()));
            }
            @Nested
            @DisplayName("and transfer amount is not greater than account balance")
            class transferAll{
                @BeforeEach
                void setUp(){
                    depositAccount.setAccountBalance(300);
                }
                @Test
                void transferToSomeoneElse(){
                    assertEquals(null,
                            transferController.createtoSomeone(transferDto, depositAccount.getIban(), savingsAccount.getIban()));
                }
            }
        }
        @Nested
        @DisplayName("transfer from depoAcc to depoAcc")
        class transferToDepoAcc{
            @BeforeEach
            void setUp(){
                depositAccount.setId(1);
                depositAccount.setIban("TR240006219445741737393617");
                depositAccount2.setId(1);
                depositAccount2.setIban("TR240006219445741737393627");
            }
            @Test
            @DisplayName("and transfer amount is greater than account balance")
            void throwsException(){
                assertThrows(ResponseStatusException.class,
                        ()-> transferController.createtoSomeone(transferDto,depositAccount.getIban(), depositAccount2.getIban()));
            }
            @Nested
            @DisplayName("and transfer amount is not greater than account balance")
            class transferAll{
                @BeforeEach
                void setUp(){
                    depositAccount.setAccountBalance(300);
                }
                @Test
                void transferToSomeoneElse(){
                    assertEquals(null,
                            transferController.createtoSomeone(transferDto, depositAccount.getIban(), depositAccount2.getIban()));
                }
            }
        }

    }
}
