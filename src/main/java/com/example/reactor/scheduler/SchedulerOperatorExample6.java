package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * subscribeOn()이 publishOn() 뒤에 위치하든 상관없이 publishOn()을 만나기 전까지의 Upstream Operator 체인은
 * subscribeOn()에서 지정한 스레드에서 실행된다.
 */
@Slf4j
public class SchedulerOperatorExample6 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer [] {1, 3, 5, 7}) // fromArray: 최상위 upstream publisher
                .doOnNext(data -> log.info("fromArray = {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .subscribeOn(Schedulers.boundedElastic())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data))
                .subscribe(data -> log.info("data = {}", data));
        Thread.sleep(500L);
    }
}
