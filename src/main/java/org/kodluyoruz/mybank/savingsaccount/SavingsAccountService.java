package org.kodluyoruz.mybank.savingsaccount;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SavingsAccountService {
    private SavingsAccountRepo savingsAccountRepo;

    public SavingsAccountService(SavingsAccountRepo savingsAccountRepo) {
        this.savingsAccountRepo = savingsAccountRepo;
    }

    public SavingsAccount get(int id){
        return savingsAccountRepo.findById(id);
    }
    public SavingsAccount get(String iban){
        return savingsAccountRepo.findByIban(iban);
    }
    public SavingsAccount create(SavingsAccount savingsAccount){
        return savingsAccountRepo.save(savingsAccount);
    }
    void delete(int id){
        savingsAccountRepo
                .deleteById(id);
    }
}
