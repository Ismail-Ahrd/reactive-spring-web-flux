package com.example.eventservice;

import lombok.*;

import java.time.Instant;

@Data @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class Event {
    private Instant instant;
    private double value;
    private Long companyId;
}
