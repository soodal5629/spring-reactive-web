package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Flux 에서 Operator 체인 사용 예제
 */
@Slf4j
public class FluxExample2 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{3, 6, 7, 9}) // 배열 형태로 데이터 발행
                .filter(n -> n > 6) // 데이터 처리 및 가공
                .map(n -> n * 2) // 데이터 처리 및 가공
                .subscribe(multiply -> log.info("# multiply: {}", multiply)); // 데이터 구독
    }
}
