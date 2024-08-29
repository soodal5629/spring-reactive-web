package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.immediate()을 적용하기 전 -> 2개의 parallel 스레드가 할당됨
 */
@Slf4j
public class SchedulersImmediateExample1 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data))
                .subscribe(data -> log.info("subscribe data = {}", data));
        Thread.sleep(200L);
    }
}
