package com.example.reactor.debugging.checkpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * checkpoint() Operator를 이용한 예제
 * 식별자를 추가해서 에러가 발생한 지점을 구분
 * 식별자를 지정할 경우에 에러가 발생한 assembly 지점의 traceback을 캡쳐 및 추가하지 않는다.
 */
@Slf4j
public class CheckpointExample5 {
    public static void main(String[] args) {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .checkpoint("### CheckpointExample5.zipWith.checkpoint")
                .map(num -> num + 2)
                .checkpoint("### CheckpointExample5.map.checkpoint")
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error));
    }
}
