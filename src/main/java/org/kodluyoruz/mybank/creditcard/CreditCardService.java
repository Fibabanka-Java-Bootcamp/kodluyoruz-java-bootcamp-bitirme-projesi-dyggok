package org.kodluyoruz.mybank.creditcard;

import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    private final CreditCardRepo creditCardRepo;

    public CreditCardService(CreditCardRepo creditCardRepo) {
        this.creditCardRepo = creditCardRepo;
    }

    public CreditCard create(CreditCard creditCard){
        return creditCardRepo.save(creditCard);
    }
    public CreditCard get(int id){return creditCardRepo.findById(id);}
}
