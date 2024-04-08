package com.example.webflux.controller;

import com.example.webflux.entities.Company;
import com.example.webflux.entities.Transaction;
import com.example.webflux.repositories.CompanyRepository;
import com.example.webflux.repositories.TransactionRepository;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class TransactionReactiveRestController {

    private TransactionRepository transactionRepository;

    private CompanyRepository companyRepository;


    public TransactionReactiveRestController(TransactionRepository transactionRepository, CompanyRepository companyRepository) {
        this.transactionRepository = transactionRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping(value = "/transactions")
    public Flux<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    @GetMapping(value = "/transactions/{id}")
    public Mono<Transaction> getOne(@PathVariable Long id){
        return transactionRepository.findById(id);
    }
    @PostMapping("/transactions")
    public Mono<Transaction> save(@RequestBody Transaction transaction){
        return transactionRepository.save(transaction);
    }
    @DeleteMapping(value = "/transactions/{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return transactionRepository.deleteById(id);
    }
    @PutMapping("/transactions/{id}")
    public Mono<Transaction> update(@RequestBody Transaction transaction, @PathVariable Long id){
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @GetMapping(value = "/streamTransactions",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> streamTransactions(){
        return transactionRepository.findAll();
    }

    @GetMapping(value = "/transactionsByCompany/{id}")
    public Flux<Transaction> transactionsBySoc(@PathVariable Long id){
        /*Company company=new Company();
        company.setId(id);*/
        return transactionRepository.findByCompanyId(id);
    }

    @GetMapping(value = "/streamTransactionsByCompany/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> stream(@PathVariable Long id){
        return companyRepository.findById(id)
                .flatMapMany(soc->{
                    Flux<Long> interval=Flux.interval(Duration.ofMillis(1000));
                    Flux<Transaction> transactionFlux= Flux.fromStream(Stream.generate(()->{
                        Transaction transaction=new Transaction();
                        transaction.setInstant(Instant.now());
                        transaction.setCompanyId(soc.getId());
                        transaction.setPrice(soc.getPrice()*(1+(Math.random()*12-6)/100));
                        return transaction;
                    }));
                    return Flux.zip(interval,transactionFlux)
                            .map(data->{
                                return data.getT2();
                            }).share();
                });
    }

    @GetMapping(value = "/events/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public  Flux<Double>   events(@PathVariable String id){
        WebClient webClient=WebClient.create("http://localhost:8082");
        Flux<Double> eventFlux=webClient.get()
                .uri("/streamEvents/"+id)
                .retrieve().bodyToFlux(Event.class)
                .map(data->data.getValue());
        return eventFlux;

    }
    @GetMapping("/test")
    public String test(){
        return Thread.currentThread().getName();
    }

}
@Data
class Event{
    private Instant instant;
    private double value;
    private Long companyId;
}