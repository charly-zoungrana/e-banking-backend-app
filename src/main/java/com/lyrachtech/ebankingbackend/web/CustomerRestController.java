package com.lyrachtech.ebankingbackend.web;

import com.lyrachtech.ebankingbackend.dtos.BankAccountDTO;
import com.lyrachtech.ebankingbackend.dtos.CustomerBankAccountsDTO;
import com.lyrachtech.ebankingbackend.dtos.CustomerDTO;
import com.lyrachtech.ebankingbackend.exceptions.CustomerNotFound;
import com.lyrachtech.ebankingbackend.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {

    private final BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    };

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name="id") Long id) throws CustomerNotFound {
        return bankAccountService.getCustomer(id);
    };

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);

    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    };

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);

    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    };

    @GetMapping("/customers/{customerId}/accounts")
    public CustomerBankAccountsDTO getCustomerBankAccounts(@PathVariable Long customerId){
        return bankAccountService.getCustomerBankAccounts(customerId);
    }
}
