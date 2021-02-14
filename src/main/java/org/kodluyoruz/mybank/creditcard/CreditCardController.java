package org.kodluyoruz.mybank.creditcard;

import org.kodluyoruz.mybank.customer.Customer;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.depositaccount.DepositAccountService;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/creditCard")
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final SavingsAccountService savingsAccountService;
    private final DepositAccountService depositAccountService;

    public CreditCardController(CreditCardService creditCardService, SavingsAccountService savingsAccountService,
                                DepositAccountService depositAccountService) {
        this.creditCardService = creditCardService;
        this.savingsAccountService = savingsAccountService;
        this.depositAccountService = depositAccountService;
    }


    @PostMapping
    public CreditCardDto create(@Valid @RequestBody CreditCardDto creditCardDto) {
        return creditCardService
                .create(creditCardDto.toCreditCards())
                .toCreditCardDto();
    }
    @GetMapping("/{id}/withAccounts/{iban}/money/{money}")
    public CreditCardDto debtOnAccount(@PathVariable int id,@PathVariable String iban, @PathVariable double money) {
        CreditCard creditCards = creditCardService.get(id);
        if (savingsAccountService.get(iban) != null) {
            SavingsAccount savingsAccount = savingsAccountService.get(iban);
            if (savingsAccount.getAccountBalance() >= money) {
                double mn = savingsAccount.getAccountBalance() - money;
                    double remainingDebt = creditCards.getDebt() + money;
                    creditCards.setDebt(remainingDebt);
                savingsAccount.setAccountBalance(mn);
                savingsAccountService.create(savingsAccount);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }

        }else{
            DepositAccount depositAccount = depositAccountService.get(iban);
            if (depositAccount.getAccountBalance() >= money) {
                double mn = depositAccount.getAccountBalance() - money;
                if (creditCards.getDebt() >= money) {
                    double remainingDebt = creditCards.getDebt() - money;
                    creditCards.setDebt(remainingDebt);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You entered too much money");
                }
                depositAccount.setAccountBalance(mn);
                depositAccountService.create(depositAccount);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }
        }
        return null;
    }

    @GetMapping("/{id}/withAtm/{money}")
    public void debtViaAtm(@PathVariable int id, @PathVariable double money){
        try {
            CreditCard creditCard = creditCardService.get(id);
            double remainingDebt = creditCard.getDebt() + money;
            creditCard.setDebt(remainingDebt);
            creditCardService.create(creditCard);
        }catch(NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Credit card not be found"+id);
        }
    }

    @GetMapping("/{id}/debtInquiry")
    public double debtInquiry(@PathVariable int id){
        CreditCard creditCard = creditCardService.get(id);
        return creditCard.getDebt();
    }

    @GetMapping("/{id}/withDrawal")
    public void shopping(@PathVariable int id, @PathVariable double money){
        try {
            CreditCard creditCard = creditCardService.get(id);
            double remainingDebt = creditCard.getDebt() - money;
            creditCard.setDebt(remainingDebt);
            creditCardService.create(creditCard);
        }catch(NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Credit card not be found"+id);
        }
    }

}
