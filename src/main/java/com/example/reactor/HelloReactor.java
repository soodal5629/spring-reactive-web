package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class HelloReactor {
    public static void main(String[] args) {
        Mono.just("### Hello Reactor") // 데이터 보내기 (publisher)
                .subscribe(message -> log.info(message)); // 데이터 구독 (subscriber)

        Flux<String> sequence = Flux.just("Hello", "Reactor"); // just: emit할 데이터를 생성 및 정의
        sequence.map(data -> data.toLowerCase()) // 데이터 가공
                .subscribe(data -> log.info(data)); // 데이터 구독 (파라미터 자체가 subscriber)

    }
}
