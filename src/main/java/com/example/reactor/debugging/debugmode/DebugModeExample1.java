package com.example.reactor.debugging.debugmode;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Non-Debug 모드
 */
@Slf4j
public class DebugModeExample1 {
    public static void main(String[] args) {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error)
                );
    }
}
