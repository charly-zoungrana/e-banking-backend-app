package com.lyrachtech.ebankingbackend.web;

import com.lyrachtech.ebankingbackend.dtos.AccountHistoryDTO;
import com.lyrachtech.ebankingbackend.dtos.AccountOperationDTO;
import com.lyrachtech.ebankingbackend.dtos.BankAccountDTO;
import com.lyrachtech.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.lyrachtech.ebankingbackend.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable  String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                                     @RequestParam(name="page",defaultValue="0") int page,
                                                     @RequestParam(name="size",defaultValue="5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

}
