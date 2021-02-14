package org.kodluyoruz.mybank.transfer;

import org.springframework.data.repository.CrudRepository;

public interface TransferRepo extends CrudRepository<Transfer,Integer> {
    Transfer findById(int id);
}
