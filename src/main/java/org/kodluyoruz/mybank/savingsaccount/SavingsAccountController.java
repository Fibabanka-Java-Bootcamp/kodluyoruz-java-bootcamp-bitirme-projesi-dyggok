package org.kodluyoruz.mybank.savingsaccount;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/savingsaccount")
public class SavingsAccountController {
    private final SavingsAccountService savingsAccountService;

    public SavingsAccountController(SavingsAccountService savingsAccountService) {
        this.savingsAccountService = savingsAccountService;
    }

    @PostMapping
    public SavingsAccountDto create(@Valid @RequestBody SavingsAccountDto savingsAccountDto){
        return savingsAccountService
                .create(savingsAccountDto.toSavingsAccount())
                .toSavingsAccountDto();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSavingsAccount(@PathVariable(value = "id") int id){
        try {
            SavingsAccount s = savingsAccountService.get(id);
            if(s.getAccountBalance() == 0){
                savingsAccountService.delete(id);
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The account cannot be deleted : " + id);
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The Account cannot be found" +id);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

