package org.kodluyoruz.mybank.transfer;

import org.kodluyoruz.mybank.depositaccount.DepositAccount;
import org.kodluyoruz.mybank.depositaccount.DepositAccountService;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccount;
import org.kodluyoruz.mybank.savingsaccount.SavingsAccountService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final TransferService transferService;
    private final RestTemplate restTemplate;
    private final SavingsAccountService savingsAccountService;
    private final DepositAccountService depositAccountService;

    public TransferController(TransferService transferService, RestTemplateBuilder restTemplateBuilder,
                              SavingsAccountService savingsAccountService,
                              DepositAccountService depositAccountService) {
        this.transferService = transferService;
        this.restTemplate = restTemplateBuilder.rootUri("https://api.exchangeratesapi.io").build();
        this.savingsAccountService = savingsAccountService;
        this.depositAccountService = depositAccountService;
    }

    @PostMapping("/betweenAccounts/{transferFrom}/{transferTo}")
    public synchronized TransferDto create (@Valid @RequestBody TransferDto transferDto,@PathVariable String transferFrom,
                               @PathVariable String transferTo) {
        transferDto = transferService.create(transferDto.toTransfer()).toTransferDto();

        if (savingsAccountService.get(transferFrom)!=null) {
            SavingsAccount s = savingsAccountService.get(transferFrom);
            DepositAccount d = depositAccountService.get(transferTo);
            double transfermoney = transferDto.getAmount();
            if(!transferDto.getTrans_type().equals(d.getCurrency())) {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("base",transferDto.getTrans_type());
                parameters.put("symbols",d.getCurrency());
                RetreivedRates rates = restTemplate.getForObject("/latest?base={base}&symbols={symbols}",
                        RetreivedRates.class,parameters);
                transferDto.setAmount((Double) rates.getRates().get(d.getCurrency())*transferDto.getAmount());
            }
            if (transferDto.getAmount() <= s.getAccountBalance()) {
                s.setAccountBalance(s.getAccountBalance() - transfermoney);
                d.setAccountBalance(transferDto.getAmount() + d.getAccountBalance());
                savingsAccountService.create(s);
                depositAccountService.create(d);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not enough money in the account");
            }
        }
        else{
            SavingsAccount s =savingsAccountService.get(transferTo);
            DepositAccount d = depositAccountService.get(transferFrom);
            double transfermoney2 = transferDto.getAmount();
            if(!transferDto.getTrans_type().equals(s.getCurrency())) {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("base",transferDto.getTrans_type());
                parameters.put("symbols",s.getCurrency());
                RetreivedRates rates = restTemplate.getForObject("/latest?base={base}&symbols={symbols}", RetreivedRates.class,parameters);
                transferDto.setAmount((Double) rates.getRates().get(s.getCurrency())*transferDto.getAmount());
            }
            if (transferDto.getAmount() <= d.getAccountBalance()) {
                d.setAccountBalance(d.getAccountBalance() - transfermoney2);
                s.setAccountBalance(transferDto.getAmount() + s.getAccountBalance());
                savingsAccountService.create(s);
                depositAccountService.create(d);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not enough money in the account");
            }
        }
        return null;
    }

    @PostMapping("/toSomeoneElse/{transferFrom}/{transferTo}")
    public synchronized TransferDto createtoSomeone (@Valid @RequestBody TransferDto transferDto,@PathVariable String transferFrom,
                               @PathVariable String transferTo) {
        transferDto = transferService.create(transferDto.toTransfer()).toTransferDto();

        if (depositAccountService.get(transferFrom) != null) {
            DepositAccount d = depositAccountService.get(transferFrom);
            SavingsAccount s1 = savingsAccountService.get(transferTo);
            DepositAccount d1 = depositAccountService.get(transferTo);
            double transfermoney = transferDto.getAmount();
            if (s1 != null) {
                if (!transferDto.getTrans_type().equals(s1.getCurrency())) {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("base", transferDto.getTrans_type());
                    parameters.put("symbols", s1.getCurrency());
                    RetreivedRates rates = restTemplate.getForObject("/latest?base={base}&symbols={symbols}", RetreivedRates.class, parameters);
                    transferDto.setAmount((Double) rates.getRates().get(s1.getCurrency()) * transferDto.getAmount());
                }
                if (transferDto.getAmount() <= d.getAccountBalance()) {
                    d.setAccountBalance(d.getAccountBalance() - transfermoney);
                    s1.setAccountBalance(transferDto.getAmount() + s1.getAccountBalance());
                    depositAccountService.create(d);
                    savingsAccountService.create(s1);

                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not enough money in the account");
                }
            } else {
                if (!transferDto.getTrans_type().equals(d1.getCurrency())) {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("base", transferDto.getTrans_type());
                    parameters.put("symbols", d1.getCurrency());
                    RetreivedRates rates = restTemplate.getForObject("/latest?base={base}&symbols={symbols}", RetreivedRates.class, parameters);
                    transferDto.setAmount((Double) rates.getRates().get(d1.getCurrency()) * transferDto.getAmount());
                }
                if (transferDto.getAmount() <= d.getAccountBalance()) {
                    d.setAccountBalance(d.getAccountBalance() - transfermoney);
                    d1.setAccountBalance(transferDto.getAmount() + d1.getAccountBalance());
                    depositAccountService.create(d);
                    depositAccountService.create(d1);

                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not enough money in the account");
                }
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"money cannot be transferred from a savings account to another account");
        }
        return null;
    }
}