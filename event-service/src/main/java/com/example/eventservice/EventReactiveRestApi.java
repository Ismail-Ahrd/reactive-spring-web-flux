package com.example.eventservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class EventReactiveRestApi {

    @GetMapping(value = "/streamEvents/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> listEvents(@PathVariable Long id) {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> Event.builder()
                .companyId(id)
                .instant(Instant.now())
                .value(100 + Math.random()*1000)
                .build()));
        return Flux.zip(interval, eventFlux).map(data -> data.getT2());
    }

}
