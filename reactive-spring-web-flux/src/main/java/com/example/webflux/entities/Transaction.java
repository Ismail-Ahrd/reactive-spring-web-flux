package com.example.webflux.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table("transactions")
public class Transaction {
    @Id
    private Long id;
    private Instant instant;
    private double price;
    private  Long companyId;
}
