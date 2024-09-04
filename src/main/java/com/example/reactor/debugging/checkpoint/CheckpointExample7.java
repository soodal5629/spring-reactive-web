package com.example.reactor.debugging.checkpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 분리된 method 에서 checkpoint() 사용 예제
 */
@Slf4j
public class CheckpointExample7 {
    public static void main(String[] args) {
        Flux<Integer> source = Flux.just(2, 4, 6, 8);
        Flux<Integer> other = Flux.just(1, 2, 3, 0);

        Flux<Integer> divideSource = divide(source, other).checkpoint();
        Flux<Integer> plusSource = plus(divideSource).checkpoint();

        plusSource.subscribe(data -> log.info("onNext = {}", data)
                , error -> log.error("onError = ", error));
    }

    private static Flux<Integer> divide(Flux<Integer> source, Flux<Integer> other) {
        return source.zipWith(other, (x, y) -> x/y);
    }

    private static Flux<Integer> plus(Flux<Integer> source) {
        return source.map(n -> n+2);
    }
}
