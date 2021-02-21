package org.kodluyoruz.mybank.prepaidcard;

import org.springframework.data.repository.CrudRepository;

public interface PrepaidCardRepo extends CrudRepository<PrepaidCard,Integer> {
    PrepaidCard findById(int id);

}
