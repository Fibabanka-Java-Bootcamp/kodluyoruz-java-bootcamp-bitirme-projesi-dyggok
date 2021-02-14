package org.kodluyoruz.mybank.transfer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {
    private final TransferRepo transferRepo;

    public TransferService(TransferRepo transferRepo) {
        this.transferRepo = transferRepo;
    }

    public Transfer create(Transfer transfer){
        return transferRepo.save(transfer);
    }

    public Transfer get(int id){
        return transferRepo.findById(id);
    }
}