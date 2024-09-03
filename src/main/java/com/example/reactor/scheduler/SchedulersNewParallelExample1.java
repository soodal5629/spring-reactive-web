package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Scheduelrs.newParallel()을 적용
 */
@Slf4j
public class SchedulersNewParallelExample1 {
    public static void main(String[] args) throws InterruptedException {
        // 2번째 파라미터: cpu 코어 수의 범위 내에서 생성한 스레드의 갯수 지정
        // 3번째 파라미터: 데몬 스레드로 동작할지 여부
        Mono<Integer> flux = Mono.just(1).publishOn(Schedulers.newParallel("Parallel Thread", 4, true));

        flux.subscribe(data -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 1 {}", data);
        });

        flux.subscribe(data -> {
            try {
                Thread.sleep(4000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 2 {}", data);
        });
        flux.subscribe(data -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 3 {}", data);
        });
        flux.subscribe(data -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 4 {}", data);
        });
        Thread.sleep(6000L);
    }
}
