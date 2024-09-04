package com.example.reactor.debugging.checkpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * checkpoint() Operator를 이용한 예제
 * 에러가 예상되는 assembly 지점에 checkpoint()를 사용해서 에러 발생 지점을 확인할 수 있음
 * traceback은 실제 에러가 발생한 assembly 지점 또는 에러가 전파된 assembly 지점의 traceback.
 * 즉, 실제 checkpoint()를 추가한 지점의 traceback이 추가된다.
 */
@Slf4j
public class CheckpointExample2 {
    public static void main(String[] args) {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x/y)
                .map(num -> num + 2)
                .checkpoint()
                .subscribe(data -> log.info("onNext = {}", data)
                            , error -> log.error("onError = ", error));
    }
}
