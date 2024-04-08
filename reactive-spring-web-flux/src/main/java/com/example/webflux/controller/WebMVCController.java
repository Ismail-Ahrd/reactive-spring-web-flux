package com.example.webflux.controller;

import com.example.webflux.repositories.CompanyRepository;
import com.example.webflux.repositories.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebMVCController {
    private CompanyRepository companyRepository;
    private TransactionRepository transactionRepository;

    public WebMVCController(CompanyRepository companyRepository, TransactionRepository transactionRepository) {
        this.companyRepository = companyRepository;
        this.transactionRepository = transactionRepository;
    }
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("societes",companyRepository.findAll());
        return "index";
    }
}
