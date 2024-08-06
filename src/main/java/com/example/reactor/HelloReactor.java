package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class HelloReactor {
    public static void main(String[] args) {
        Mono.just("### Hello Reactor") // 데이터 보내기 (publisher)
                .subscribe(message -> log.info(message)); // 데이터 구독 (subscriber)
    }
}
