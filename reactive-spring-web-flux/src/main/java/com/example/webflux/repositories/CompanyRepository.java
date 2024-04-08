package com.example.webflux.repositories;

import com.example.webflux.entities.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CompanyRepository extends ReactiveCrudRepository<Company, Long> {
    Flux<Company> findByName(String name);
}
