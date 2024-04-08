package com.example.webflux.controller;

import com.example.webflux.entities.Company;
import com.example.webflux.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CompanyReactiveRestController {
    @Autowired
    private CompanyRepository companyRepository;
    @GetMapping(value = "/companies")
    public Flux<Company> findAll(){
        return companyRepository.findAll();
    }
    @GetMapping(value = "/companies/{id}")
    public Mono<Company> getOne(@PathVariable Long id){
        return companyRepository.findById(id);
    }
    @PostMapping("/companies")
    public Mono<Company> save(@RequestBody Company company){
        return companyRepository.save(company);
    }
    @DeleteMapping(value = "/companies/{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return companyRepository.deleteById(id);
    }
    @PutMapping("/companies/{id}")
    public Mono<Company> update(@RequestBody Company company, @PathVariable Long id){
        company.setId(id);
        return companyRepository.save(company);
    }
}
