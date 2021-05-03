package com.k.httpbankservice.controllers;

import com.k.httpbankservice.models.Bank;
import com.k.httpbankservice.models.BankAccount;
import com.k.httpbankservice.models.Client;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
public class BankAccountController {
    @GetMapping("/{id}/clients/{idC}/accounts")
    public String allAccounts(Model model, @PathVariable("id") Long id, @PathVariable("idC") Long idC) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<BankAccount>> response = restTemplate
                .exchange("http://localhost:8080/" + id + "/clients/" + idC + "/accounts",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BankAccount>>() {
                        });
        List<BankAccount> accountList = response.getBody();

        model.addAttribute("accountsList", accountList);
        model.addAttribute("idd", id);
        model.addAttribute("idC", idC);
        return "accounts";
    }

    @GetMapping("/{id}/clients/{idC}/accounts/newAccount")
    public String newAccount(Model model, @PathVariable("id") String idB, @PathVariable("idC") Long idC) {
        model.addAttribute("idB", idB);
        model.addAttribute("idC", idC);
        model.addAttribute("account", new BankAccount());
        return "newAccount";
    }

    @PostMapping("/{id}/clients/{idC}/accounts/newAccount")
    public String submitNewAccount(@ModelAttribute("account") BankAccount bankAccount, @PathVariable("id") Long id, @PathVariable String idC) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .exchange("http://localhost:8080/" + id + "/clients/" + idC + "/accounts/newAccount",
                        HttpMethod.POST, new HttpEntity<BankAccount>(bankAccount),
                        BankAccount.class);
        return "redirect:";
    }

    @GetMapping("/{id}/clients/{idC}/accounts/{idAc}")
    String showAccount(@PathVariable("id") Long id, @PathVariable("idC") Long idC, Model model, @PathVariable String idAc) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BankAccount> response = restTemplate.exchange("http://localhost:8080/" + id + "/clients/" + idC + "/accounts/" + idAc,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<BankAccount>() {
                });
        model.addAttribute("account", response.getBody());
        return "showAccount";
    }

    @PostMapping("/{id}/clients/{idC}/accounts/{idAc}")
    public String deleteAccount(@PathVariable("id") Long id, @PathVariable("idC") Long idC, @PathVariable String idAc) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id+"/clients/"+idC+"/accounts/"+idAc,
                HttpMethod.DELETE, null,
                new ParameterizedTypeReference<BankAccount>() {
                });

        return "redirect:";
    }

    @GetMapping("/{id}/clients/{idC}/accounts/{idAc}/editAccount")
    public String updateAccountGet(Model model, @PathVariable("id") Long id, @PathVariable("idC") Long idC, @PathVariable String idAc) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BankAccount> response = restTemplate.exchange("http://localhost:8080/" + id + "/clients/" + idC+"/accounts/"+idAc,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<BankAccount>() {
                });
        model.addAttribute("account", response.getBody());
        model.addAttribute("id", id);
        model.addAttribute("idC", idC);
        model.addAttribute("idAc", idAc);
        return "editAccount";
    }
    @PostMapping("/{id}/clients/{idC}/accounts/{idAc}/editAccount")
    public String updateAccount(@ModelAttribute("account") BankAccount bankAccount, @PathVariable("idC") Long idC, @PathVariable("id") Long id, @PathVariable String idAc){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id + "/clients/"+idC+"/accounts/"+idAc+"/editAccount",
                HttpMethod.PUT, new HttpEntity<BankAccount>(bankAccount),
                new ParameterizedTypeReference<BankAccount>() {
                });
        return "redirect:";
    }

}
