package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * subscribeOn()은 구독 직후에 실행 될 스레드를 지정한다.
 * 즉, 원본 Publisher의 실행 스레드를 subscribeOn()에서 지정한 스레드로 바꾼다.
 */
@Slf4j
public class SchedulerOperatorExample4 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer [] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("fromArray = {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data))
                .subscribe(data -> log.info("data = {}", data));
        Thread.sleep(500L);
    }
}
