package org.kodluyoruz.mybank.savingsaccount;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SavingsAccountRepo extends CrudRepository<SavingsAccount, Integer> {
    SavingsAccount findByIban(String iban);
    SavingsAccount findById(int id);
    void deleteById(int id);
}
