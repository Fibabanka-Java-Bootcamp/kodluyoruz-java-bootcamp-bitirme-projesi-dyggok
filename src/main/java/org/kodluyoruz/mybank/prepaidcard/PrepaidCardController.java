package org.kodluyoruz.mybank.prepaidcard;

import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.depositaccount.DepositAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/prepaidCard")
public class PrepaidCardController {
    private final PrepaidCardService prepaidCardService;
    private final DepositAccountService depositAccountService;

    public PrepaidCardController(PrepaidCardService prepaidCardService,
                                 DepositAccountService depositAccountService) {
        this.prepaidCardService = prepaidCardService;
        this.depositAccountService = depositAccountService;
    }

    @PostMapping
    public PrepaidCardDto create(@Valid @RequestBody PrepaidCardDto prepaidCardDto) {
        return prepaidCardService
                .create(prepaidCardDto.toCreditCards())
                .toPrepaidDto();
    }

    @GetMapping("/{id}/fromAccount/{money}")
    public synchronized boolean moneyFromAccount(@PathVariable int id, @PathVariable double money) {
        try {
            DepositAccount d = depositAccountService.getByCard(id);
            if (d.getAccountBalance() >= money) {
                d.setAccountBalance(d.getAccountBalance() - money);
                depositAccountService.create(d);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not enough money in the account");
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The account not be found");
        }
        return true;
    }
}