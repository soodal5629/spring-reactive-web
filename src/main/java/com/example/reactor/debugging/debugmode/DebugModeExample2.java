package com.example.reactor.debugging.debugmode;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

/**
 * onOperatorDebug() Hook 메소드를 이용한 디버그 모드
 * 어플리케이션 전체에서 global 하게 동작함
 */
@Slf4j
public class DebugModeExample2 {
    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error)
                );
    }
}
