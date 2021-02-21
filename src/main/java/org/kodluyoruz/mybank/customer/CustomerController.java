package org.kodluyoruz.mybank.customer;

import org.kodluyoruz.mybank.depositaccount.DepositAccountService;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountService;
import org.springframework.web.bind.annotation.RestController;

import org.kodluyoruz.mybank.creditcard.CreditCard;
import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final DepositAccountService depositAccountService;
    private final SavingsAccountService savingsAccountService;

    public CustomerController(CustomerService customerService, DepositAccountService depositAccountService,
                              SavingsAccountService savingsAccountService) {
        this.customerService = customerService;
        this.depositAccountService = depositAccountService;
        this.savingsAccountService = savingsAccountService;
    }

    @PostMapping
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto){
        return customerService
                .create(customerDto.toCustomer())
                .toCustomerDto();
    }

    @GetMapping(value = "/{firstname}/{lastname}")
    public List<CustomerDto> list(@PathVariable String firstname,
                                  @PathVariable String lastname){
        return customerService.get(firstname,lastname).stream()
                .map(Customer::toCustomerDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") int id){
        try {
            Customer c1 = customerService.get(id);
            DepositAccount d1 = depositAccountService.getByCus(id);
            SavingsAccount s1 = savingsAccountService.getByCus(id);
            CreditCard card = c1.getCreditCard();
            if (d1 !=null) {
                if(d1.getAccountBalance() !=0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Customer cannot be deleted");
                }
            }
            if(s1 !=null) {
                if(s1.getAccountBalance() !=0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Customer cannot be deleted");
                }
            }
            if(card !=null){
                if(card.getDebt()<0){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Customer cannot be deleted");
                }
            }
            else{
                customerService.delete(id);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return response;
            }
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not be found" +id);
        }
    return null;
    }
}


