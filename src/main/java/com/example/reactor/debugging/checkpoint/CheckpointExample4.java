package com.example.reactor.debugging.checkpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * checkpoint() Operator를 이용한 예제
 * description을 추가해서 에러가 발생한 지점 구분 가능
 * description을 지정할 경우에 에러가 발생한 assembly 지점의 trackback을 추가하지 않는다.
 */
@Slf4j
public class CheckpointExample4 {
    public static void main(String[] args) {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .checkpoint("### CheckpointExample4.zipWith.checkpoint")
                .map(num -> num + 2)
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error));
    }
}
