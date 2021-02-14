package org.kodluyoruz.mybank.depositaccount;

import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountDto;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/depositaccount")
public class DepositAccountController {

    private final DepositAccountService depositAccountService;

    public DepositAccountController(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    @PostMapping
    public DepositAccountDto create(@Valid @RequestBody DepositAccountDto depositAccountDto){
        return depositAccountService
                .create(depositAccountDto.toDepositAccount())
                .toDepositAccountDto();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteDepositAccount(@PathVariable(value = "id") int id){
        try {
            DepositAccount d = depositAccountService.get(id);
            if (d.getAccountBalance() == 0) {
                depositAccountService.delete(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The account cannot be deleted : " + id);
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The account not be found");
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

