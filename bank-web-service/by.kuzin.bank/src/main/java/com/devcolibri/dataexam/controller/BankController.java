package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.Bank;
import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.entity.Worker;
import com.devcolibri.dataexam.service.BankAccountService;
import com.devcolibri.dataexam.service.BankService;
import com.devcolibri.dataexam.service.ClientService;
import com.devcolibri.dataexam.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
public class BankController {

    private BankService bankService;
    private ClientService clientService;
    private BankAccountService bankAccountService;
    private WorkerService workerService;


    @Autowired
    public BankController(BankService bankService, ClientService clientService, BankAccountService bankAccountService, WorkerService workerService) {
        this.clientService = clientService;
        this.bankService = bankService;
        this.bankAccountService = bankAccountService;
        this.workerService = workerService;
    }

    @GetMapping("/")
    List<Bank> allBanks() {
        return bankService.getAll();
    }


    @PostMapping("/newBank")
    void newBankSubmit(@RequestBody Bank bank) {
        bankService.addBank(bank);
    }


    @GetMapping("/{id}")
    Bank showBank(@PathVariable("id") Long id) {
        return bankService.getById(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            if (id.equals(client.getBank().getId())) {
                Long clientId = client.getId();
                List<BankAccount> bankAccounts = bankAccountService.getAll();
                for (BankAccount account : bankAccounts) {
                    if (clientId.equals(account.getClient().getId())) {
                        Long accountId = account.getId();
                        bankAccountService.delete(accountId);
                    }
                }
                clientService.delete(clientId);
            }
        }
        List<Worker> workers = workerService.getAll();
        for (Worker worker : workers
        ) {
            if (id.equals(worker.getBank().getId())) {
                Long workerId=worker.getId();
                workerService.delete(workerId);
            }
        }

        bankService.delete(id);
    }


    @PutMapping("/{id}/edit")
    public void update(@RequestBody Bank bank) {
        bankService.addBank(bank);
    }
}
