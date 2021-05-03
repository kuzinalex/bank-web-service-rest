package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.entity.Worker;
import com.devcolibri.dataexam.service.BankService;
import com.devcolibri.dataexam.service.WorkerService;
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
public class WorkerController {

    private WorkerService workerService;
    private BankService bankService;

    @Autowired
    public WorkerController(WorkerService workerService, BankService bankService) {
        this.workerService = workerService;
        this.bankService = bankService;
    }

    @GetMapping("/{id}/workers")
    List<Worker> allWorkers(Model model, @PathVariable Long id) {
        return workerService.findWorkersByBankId(id);
    }

    @PostMapping("/{id}/workers/newWorker")
    void submitNewWorker(@RequestBody Worker worker, @PathVariable("id") Long id) {
        Long maxId=workerService.getAll().stream().map(Worker::getId).max(Long::compareTo).orElse(0L);
        worker.setBank(bankService.getById(id));
        workerService.addWorker(worker);
    }

    @GetMapping("/{id}/workers/{idW}")
    Worker showWorker(@PathVariable("idW") Long id) {
        return workerService.getById(id);
    }

    @DeleteMapping("/{id}/workers/{idW}")
    void deleteWorker(@PathVariable("idW") Long id) {
        workerService.delete(id);
    }

    @PutMapping("/{id}/workers/{idW}/editWorker")
    public void updateWorker(@RequestBody Worker worker, @PathVariable("idW") Long idW,@PathVariable("id") Long id) {

        worker.setBank(bankService.getById(id));
        workerService.editWorker(worker, idW);
    }
}
