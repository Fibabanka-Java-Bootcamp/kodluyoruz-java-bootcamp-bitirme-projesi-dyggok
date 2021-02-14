package org.kodluyoruz.mybank.prepaidcard;

import org.springframework.stereotype.Service;

@Service
public class PrepaidCardService {
    private final PrepaidCardRepo prepaidCardRepo;

    public PrepaidCardService(PrepaidCardRepo prepaidCardRepo) {
        this.prepaidCardRepo = prepaidCardRepo;
    }

    public PrepaidCard create(PrepaidCard prepaidCard){
        return prepaidCardRepo.save(prepaidCard);
    }
}
