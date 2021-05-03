package com.k.httpbankservice.controllers;

import com.k.httpbankservice.models.Bank;
import com.k.httpbankservice.models.Client;
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
public class ClientController {

    @GetMapping("/{id}/clients")
    public String allClients(Model model, @PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Client>> response = restTemplate
                .exchange("http://localhost:8080/" + id + "/clients",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Client>>() {
                        });
        List<Client> clientList = response.getBody();

        model.addAttribute("clients", clientList);
        model.addAttribute("idd", id);
        return "clients";
    }


    @GetMapping("/{id}/clients/newClient")
    public String newClient(Model model, @PathVariable String id) {
        model.addAttribute("idB", id);
        model.addAttribute("client", new Client());
        return "newClient";
    }

    @GetMapping("/{id}/clients/{idC}")
    String showClient(@PathVariable("id") Long id, @PathVariable("idC") Long idC, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Client> response = restTemplate.exchange("http://localhost:8080/" + id + "/clients/" + idC,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Client>() {
                });
        model.addAttribute("client", response.getBody());
        return "showClient";
    }

    @PostMapping("/{id}/clients/newClient")
    public String submitNewClient(@ModelAttribute("client") Client client, @PathVariable("id") Long id) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .exchange("http://localhost:8080/" + id + "/clients/newClient",
                        HttpMethod.POST, new HttpEntity<Client>(client),
                        Client.class);
        return "redirect:";
    }

    @PostMapping("/{id}/clients/{idC}")
    public String deleteClient(@PathVariable("id") Long id, @PathVariable("idC") Long idC) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id+"/clients/"+idC,
                HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Client>() {
                });

        return "redirect:";
    }


    @GetMapping("/{id}/clients/{idC}/editClient")
    public String updateClientGet(Model model , @PathVariable("id") Long id, @PathVariable("idC") Long idC) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Client> response = restTemplate.exchange("http://localhost:8080/" + id + "/clients/" + idC,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Client>() {
                });
            model.addAttribute("client", response.getBody());
            model.addAttribute("id", id);
            model.addAttribute("idC", idC);
        return "editClient";
    }
    @PostMapping("/{id}/clients/{idC}/editClient")
    public String updateClient(@ModelAttribute("client") Client client, @PathVariable("idC") Long idC, @PathVariable("id") Long id){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id + "/clients/"+idC+"/editClient",
                HttpMethod.PUT, new HttpEntity<Client>(client),
                new ParameterizedTypeReference<Client>() {
                });
        return "redirect:";
    }
}
