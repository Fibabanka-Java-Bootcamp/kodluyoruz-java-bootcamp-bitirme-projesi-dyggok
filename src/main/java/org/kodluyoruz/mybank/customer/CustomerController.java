package org.kodluyoruz.mybank.customer;

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

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto){
        Customer customer = new Customer();
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
            DepositAccount d1 = c1.getDepositAccount();
            SavingsAccount s1 = c1.getSavingsAccount();
            CreditCard creditCard = c1.getCreditCards();
            if (d1 == null && s1 == null && creditCard == null) {
                customerService.delete(id);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return response;
            }else if (d1.getAccountBalance() != 0 || s1.getAccountBalance() != 0 || creditCard.getDebt() != 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Customer cannot be deleted");
            }
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer cannot be found"+id);
        }
        return null;
    }
}


