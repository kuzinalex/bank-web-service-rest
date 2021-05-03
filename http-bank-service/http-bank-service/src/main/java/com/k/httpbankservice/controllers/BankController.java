package com.k.httpbankservice.controllers;

import com.k.httpbankservice.models.Bank;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
public class BankController {

    @GetMapping("/")
    public String allBanks(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Bank>> response = restTemplate
                .exchange("http://localhost:8080/",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Bank>>() {
                        });
        List<Bank> banks = response.getBody();
        model.addAttribute("banks", banks);
        return "banks";
    }

    @GetMapping("/{id}")
    String showBank(@PathVariable("id") Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Bank> response = restTemplate
                .exchange("http://localhost:8080/" + id,
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<Bank>() {
                        });
        Bank bank = response.getBody();
        model.addAttribute("bank", bank);
        return "showBank";
    }

    @GetMapping("/newBank")
    public String newBank(Model model) {
        model.addAttribute("bank", new Bank());
        return "newBank";
    }


    @PostMapping("/newBank")
    public String newBankSubmit(Bank bank) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .exchange("http://localhost:8080/newBank",
                        HttpMethod.POST, new HttpEntity<Bank>(bank), Bank.class);
        return "redirect:";
    }


    @PostMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id,
                        HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Bank>() {
                        });
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String updateGet(@ModelAttribute("bank") Bank bank) {

        return "editBank";
    }

    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute("bank") Bank bank) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + bank.getId() + "/edit",
                HttpMethod.PUT, new HttpEntity<Bank>(bank),
                new ParameterizedTypeReference<Bank>() {
                });
        return "redirect:";
    }
}
