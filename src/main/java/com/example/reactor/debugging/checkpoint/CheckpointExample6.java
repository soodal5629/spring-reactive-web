package com.example.reactor.debugging.checkpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * checkpoint(description, true/false) Operator를 이용한 예제
 * description도 사용하고 에러가 발생한 assembly 지점 또는 에러가 전파된 assembly 지점의 traceback도 추가
 */
@Slf4j
public class CheckpointExample6 {
    public static void main(String[] args) {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .checkpoint("### CheckpointExample6.zipWith.checkpoint", true)
                .map(num -> num + 2)
                .checkpoint("### CheckpointExample6.map.checkpoint", true)
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error));
    }
}
