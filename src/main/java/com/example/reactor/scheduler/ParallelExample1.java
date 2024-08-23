package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * parallel()만 사용할 경우에는 병렬로 작업을 수행하지 않는다.
 */
@Slf4j
public class ParallelExample1 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15})
                // 얘만 쓰면 병렬로 처리되지 않고 메인 쓰레드에서 전부 처리된다.
                .parallel()
                .subscribe(data -> log.info("onNext(): {}", data));
    }
}