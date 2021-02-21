package org.kodluyoruz.mybank.depositaccount;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepositAccountRepo extends CrudRepository<DepositAccount, Integer> {
    DepositAccount findById(int id);
    DepositAccount findByIban(String iban);
    DepositAccount findByCustomer_Id(int id);
    void deleteById(int id);
    DepositAccount findByPrepaidCard_Id(int id);
}
