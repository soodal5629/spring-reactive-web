package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ColdSequenceExample {
    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "BLUE")) // fromIterable -> 데이터 emit
                .map(String::toLowerCase);
        coldFlux.subscribe(country -> log.info("# Subscriber1: {}", country)); // 1st 구독
        log.info("====================");
        coldFlux.subscribe(country -> log.info("# Subscriber2: {}", country)); // 2nd 구독
    }
}
