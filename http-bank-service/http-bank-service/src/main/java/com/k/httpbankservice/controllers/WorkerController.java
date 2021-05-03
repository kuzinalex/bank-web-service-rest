package com.k.httpbankservice.controllers;

import com.k.httpbankservice.models.Client;
import com.k.httpbankservice.models.Worker;
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
public class WorkerController {

    @GetMapping("/{id}/workers")
    public String allClients(Model model, @PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Worker>> response = restTemplate
                .exchange("http://localhost:8080/" + id + "/workers",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Worker>>() {
                        });
        List<Worker> workerList = response.getBody();

        model.addAttribute("workers", workerList);
        model.addAttribute("idd", id);
        return "workers";
    }

    @GetMapping("/{id}/workers/newWorker")
    public String newWorker(Model model, @PathVariable("id") Long id) {
        model.addAttribute("idB", id);
        model.addAttribute("worker", new Worker());
        return "newWorker";
    }

    @PostMapping("/{id}/workers/newWorker")
    public String submitNewWorker(@ModelAttribute("worker") Worker worker, @PathVariable("id") Long id) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .exchange("http://localhost:8080/" + id + "/workers/newWorker",
                        HttpMethod.POST, new HttpEntity<Worker>(worker),
                        Worker.class);
        return "redirect:";
    }

    @GetMapping("/{id}/workers/{idW}")
    String showWorker(@PathVariable("id") Long id, @PathVariable("idW") Long idW, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Worker> response = restTemplate.exchange("http://localhost:8080/" + id + "/workers/" + idW,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Worker>() {
                });
        model.addAttribute("worker", response.getBody());
        return "showWorker";
    }

    @GetMapping("/{id}/workers/{idW}/editWorker")
    public String updateWorkerGet(Model model , @PathVariable("id") Long id, @PathVariable("idW") Long idW) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Worker> response = restTemplate.exchange("http://localhost:8080/" + id + "/workers/" + idW,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Worker>() {
                });
        model.addAttribute("worker", response.getBody());
        model.addAttribute("id", id);
        model.addAttribute("idW", idW);
        return "editWorker";
    }

    @PostMapping("/{id}/workers/{idW}/editWorker")
    public String updateClient(@ModelAttribute("worker") Worker worker, @PathVariable("idW") Long idW, @PathVariable("id") Long id){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id + "/workers/"+idW+"/editWorker",
                HttpMethod.PUT, new HttpEntity<Worker>(worker),
                new ParameterizedTypeReference<Worker>() {
                });
        return "redirect:";
    }

    @PostMapping("/{id}/workers/{idW}")
    public String deleteWorker(@PathVariable("id") Long id, @PathVariable("idW") Long idW) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/" + id+"/workers/"+idW,
                HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Worker>() {
                });

        return "redirect:";
    }
}
