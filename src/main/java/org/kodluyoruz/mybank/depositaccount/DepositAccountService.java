package org.kodluyoruz.mybank.depositaccount;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DepositAccountService {

    private final DepositAccountRepo depositAccountRepo;

    public DepositAccountService(DepositAccountRepo depositAccountRepo) {
        this.depositAccountRepo = depositAccountRepo;
    }

    public DepositAccount get(int id){
        return depositAccountRepo.findById(id);
    }
    public DepositAccount get(String iban){return depositAccountRepo.findByIban(iban);}
    public DepositAccount create(DepositAccount depositAccount){
        return depositAccountRepo.save(depositAccount);
    }
    public void delete(int id){ depositAccountRepo.deleteById(id);}
    public DepositAccount getByCus(int id){return depositAccountRepo.findByCustomer_Id(id);}
    public DepositAccount getByCard(int id){return depositAccountRepo.findByPrepaidCard_Id(id);}
}


