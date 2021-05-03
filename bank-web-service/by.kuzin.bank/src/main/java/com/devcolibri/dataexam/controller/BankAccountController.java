package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.service.BankAccountService;
import com.devcolibri.dataexam.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@EnableWebMvc
public class BankAccountController {

    private BankAccountService bankAccountService;
    private ClientService clientService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, ClientService clientService) {
        this.bankAccountService = bankAccountService;
        this.clientService = clientService;
    }

    @GetMapping("/{id}/clients/{idC}/accounts")
    List<BankAccount> allAccounts(@PathVariable("idC") Long idC, @PathVariable("id") Long id) {
        List<BankAccount> accounts = bankAccountService.getAll();
        List<BankAccount> accountList = new ArrayList<>();
        for (BankAccount account : accounts
        ) {
            if (idC.equals(account.getClient().getId())) {
                accountList.add(account);
            }
        }
        return accountList;
    }

    @PostMapping("/{id}/clients/{idC}/accounts/newAccount")
    void submitNewAccount(@RequestBody BankAccount bankAccount, @PathVariable("idC") Long idC) {
        Long maxId=bankAccountService.getAll().stream().map(BankAccount::getId).max(Long::compareTo).orElse(0L);
        bankAccount.setClient(clientService.getById(idC));
        bankAccountService.addBankAccount(bankAccount);
    }

    @GetMapping("/{id}/clients/{idC}/accounts/{idAc}")
    BankAccount showAccount(Model model, @PathVariable("idAc") Long idAc,@PathVariable("id") Long id, @PathVariable("idC") Long idC) {
        return bankAccountService.getById(idAc);
    }
    @DeleteMapping("/{id}/clients/{idC}/accounts/{idAc}")
    public void deleteAccount(@PathVariable Long idAc){
        bankAccountService.delete(idAc);
    }

    @PutMapping("/{id}/clients/{idC}/accounts/{idAc}/editAccount")
    public void updateAccount(@RequestBody BankAccount bankAccount,@PathVariable("idC") Long idC, @PathVariable("idAc") Long idAc){

        bankAccount.setClient(clientService.getById(idC));
        bankAccountService.editBankAccount(bankAccount,idAc);
    }
}
