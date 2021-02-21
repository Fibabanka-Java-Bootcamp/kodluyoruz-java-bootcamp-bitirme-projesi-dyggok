package org.kodluyoruz.mybank.customer;
import org.springframework.data.repository.CrudRepository;

import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
    List<Customer> findByFirstnameAndLastname(String firstname, String lastname);
    Customer findByPhone(String phone);
    Customer findById(int id);
    Customer deleteById(int id);
    Customer findCustomerBySavingsAccount(SavingsAccount savingsAccount);
    Customer findCustomerByDepositAccount(DepositAccount depositAccount);


}

