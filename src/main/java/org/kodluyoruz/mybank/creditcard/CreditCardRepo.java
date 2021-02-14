package org.kodluyoruz.mybank.creditcard;

import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepo extends CrudRepository<CreditCard,Integer> {
    CreditCard findById(int id);
}
