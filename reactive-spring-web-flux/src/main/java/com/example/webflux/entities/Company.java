package com.example.webflux.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data @AllArgsConstructor @NoArgsConstructor @ToString @Builder
@Table("companies")
public class Company {
    @Id
    private Long id;
    private String name;
    private double price;
}
