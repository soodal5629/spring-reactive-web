package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.immediate()을 적용 후
 * 현재의 스레드가 할당됨
 */
@Slf4j
public class SchedulersImmediateExample2 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .publishOn(Schedulers.immediate())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data))
                .subscribe(data -> log.info("subscribe data = {}", data));
        Thread.sleep(200L);
    }
}
