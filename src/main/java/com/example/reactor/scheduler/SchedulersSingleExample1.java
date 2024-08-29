package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.single()을 적용할 경우, Schedulers.single()에서 할당된 스레드를 재사용한다.
 * -> 스레드 하나만 생성해서 재사용
 */
@Slf4j
public class SchedulersSingleExample1 {
    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
                .subscribe(data -> log.info("onNext: {}", data));
        doTask("task2")
                .subscribe(data -> log.info("onNext: {}", data));
        Thread.sleep(200L);
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.single()) // Reactor에서 제공하는 default 인스턴스를 사용
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data));
    }
}
