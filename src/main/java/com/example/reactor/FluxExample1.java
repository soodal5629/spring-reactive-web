package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxExample1 {
    public static void main(String[] args) {
        Flux.just(3, 6, 9) // 데이터 발행
                .map(n -> n % 2) // 데이터 처리 및 가공
                .subscribe(remainder -> log.info("# remainder: {}", remainder)); // 데이터 구독
    }
}
