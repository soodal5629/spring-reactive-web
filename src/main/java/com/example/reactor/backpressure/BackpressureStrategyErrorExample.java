package com.example.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
/**
 * Unbounded reqeust일 경우, Downstream에 Backpressure Error 전략을 사용하는 예제
 * - Downstream 으로 전달 할 데이터가 버퍼에 가득 찰 경우, Exception 을 발생시키는 전략
 */
public class BackpressureStrategyErrorExample {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(1L)) // 숫자 0부터 1씩 증가하며 데이터 emit
                .onBackpressureError() // 에러 전략 적용
                .doOnNext(data -> log.info("emit data = {}", data)) // upstream에서 emit한 데이터를 출력
                .publishOn(Schedulers.parallel()) // publishOn -> 새로운 스레드 하나 추가
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            log.info("value - {}", data);
                },
                error -> log.error(error.getMessage()));
        Thread.sleep(2000L);
    }
}
