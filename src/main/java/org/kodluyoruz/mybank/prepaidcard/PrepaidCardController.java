package org.kodluyoruz.mybank.prepaidcard;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/prepaidCard")
public class PrepaidCardController {
    private final PrepaidCardService prepaidCardService;

    public PrepaidCardController(PrepaidCardService prepaidCardService) {
        this.prepaidCardService = prepaidCardService;
    }

    @PostMapping
    public PrepaidCardDto create(@Valid @RequestBody PrepaidCardDto prepaidCardDto) {
        return prepaidCardService
                .create(prepaidCardDto.toCreditCards())
                .toPrepaidDto();
    }
}
