package com.example.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
/**
 * Unbounded reqeust일 경우, Downstream에 Backpressure Latest 전략을 사용하는 예제
 * - Downstream 으로 전달 할 데이터가 버퍼에 가득 찰 경우,
 *   버퍼 밖에서 폐기되지 않고 대기하는 가장 나중에(최근에) emit 된 데이터를 버퍼에 채우는 전략
 */
public class BackpressureStrategyLatestExample {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(1L)) // 숫자 0부터 1씩 증가하며 데이터 emit
                // latest 전략 적용
                .onBackpressureLatest()
                .publishOn(Schedulers.parallel()) // publishOn -> 새로운 스레드 하나 추가
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                                log.info("value - {}", data);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                },
                error -> log.error("error class ={}, e.message = {}", error.getClass(), error.getMessage()));
        Thread.sleep(2000L);
    }
}
