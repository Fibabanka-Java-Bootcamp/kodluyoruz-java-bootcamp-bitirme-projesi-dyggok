package org.kodluyoruz.mybank.customer;

import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer create(Customer customer){
        return customerRepo.save(customer);
    }
    public List<Customer> get(String firstname, String lastname){
        return customerRepo.findByFirstnameAndLastname(firstname,lastname);
    }
    public Customer getPhone(String phone){
        return customerRepo.findByPhone(phone);
    }
    public void delete(int id){
        customerRepo.deleteById(id);
    }
    public Customer get(int id){ return customerRepo.findById(id);}
    public Customer get(DepositAccount depositAccount){return customerRepo.findCustomerByDepositAccount(depositAccount);}
}


