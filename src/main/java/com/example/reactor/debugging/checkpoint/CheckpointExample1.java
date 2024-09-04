package com.example.reactor.debugging.checkpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * checkpoint() Operator를 이용한 예제
 * 에러가 예상되는 assembly 지점에 checkpoint()를 사용해서 에러 발생 지점을 확인할 수 있음
 * checkpoint()는 에러 발생 시, traceback에 추가됨
 */
@Slf4j
public class CheckpointExample1 {
    public static void main(String[] args) {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .checkpoint()
                .map(num -> num + 2)
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error));
    }
}
