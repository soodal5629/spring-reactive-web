package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 여러 개의 Flux를 연결해서 하나의 Flux로 결합하는 예제
 */
@Slf4j
public class FluxExample4 {
    public static void main(String[] args) {
        Flux.concat(
                Flux.just("Venus"),
                Flux.just("Earth"),
                Flux.just("Mars"),
                Mono.just("Moon") // Mono도 concat 안에 들어갈 수 있음
                )
            .collectList()
            .subscribe(data -> log.info("# data: {}", data)); // 데이터 구독
    }
}
