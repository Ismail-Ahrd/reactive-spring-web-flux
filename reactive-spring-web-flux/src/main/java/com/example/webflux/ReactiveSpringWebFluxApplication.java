package com.example.webflux;

import com.example.webflux.entities.Company;
import com.example.webflux.entities.Transaction;
import com.example.webflux.repositories.CompanyRepository;
import com.example.webflux.repositories.TransactionRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
//@EnableR2dbcRepositories
//@EnableWebFlux
public class ReactiveSpringWebFluxApplication {



	public static void main(String[] args) {
		SpringApplication.run(ReactiveSpringWebFluxApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CompanyRepository companyRepository,
							TransactionRepository transactionRepository){
		return args->{
			transactionRepository.deleteAll().subscribe(null,null,()->{
				companyRepository.deleteAll().subscribe(null,null,()->{
					Stream.of("SG","AWB","BMCE","AXA").forEach(s->{
						Company company = Company.builder()
								.name(s)
								.price(100+Math.random()*900)
								.build();
						companyRepository.save(company)
								.subscribe(soc->{
									System.out.println(soc.toString());
								});
					});
					Stream.of("SG","AWB","BMCE","AXA").forEach(s->{
						companyRepository.findByName(s).subscribe(soc->{
							for (int i = 0; i <10 ; i++) {
								Transaction transaction= Transaction
										.builder()
										.companyId(soc.getId())
										.price(soc.getPrice()*(1+(Math.random()*12-6)/100))
										.instant(Instant.now())
										.build();
								transactionRepository.save(transaction).subscribe(t->{
									System.out.println(t.toString());
								});
							}
						});
					});
				});
			});
			System.out.println("......");

		};
	}
	/*@Bean
	CommandLineRunner start(CompanyRepository companyRepository, TransactionRepository transactionRepository) {
		return args -> {
			companyRepository.deleteAll()
					.thenMany(Flux.just("SG", "AWB", "BMCE", "AXA")
							.flatMap(name -> companyRepository.save(new Company(name, name, 100 + Math.random() * 900)))
							.doOnNext(System.out::println)
							.then(transactionRepository.deleteAll())
							.thenMany(companyRepository.findAll())
							.flatMap(company -> Flux.range(0, 10)
									.map(i -> {
										Transaction transaction = new Transaction();
										transaction.setInstant(Instant.now());
										transaction.setCompanyId(company.getId());
										transaction.setPrice(company.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
										return transaction;
									})
									.flatMap(transactionRepository::save)
									.doOnNext(System.out::println))
							.then()
					)
					.then(Mono.defer(() -> {
						System.out.println("Initialization completed.");
						return Mono.empty();
					}))
					.subscribe();
		};
	}*/



}
