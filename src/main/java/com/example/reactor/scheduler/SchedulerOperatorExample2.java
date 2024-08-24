package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Sequence의 Operator 체인에서 publishOn()이 호출되면 publishOn() 호출 이후의 Operator 체인은
 * 다음 publishOn()을 만나기 전까지 publishOn()에서 지정한 스레드에서 실행됨
 */
@Slf4j
public class SchedulerOperatorExample2 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer [] {1, 3, 5, 7})
                .doOnNext(data -> log.info("fromArray = {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data))
                .subscribe(data -> log.info("data = {}", data));
    }
}
