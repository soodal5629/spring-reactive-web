package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.single()을 적용 후, 첫번째 Schedulers.single()에서 할당된 스레드를 재사용한다.
 * newSingle 메소드는 호출할 때마다 새로운 스레드 하나를 생성
 */
@Slf4j
public class SchedulersSingleExample2 {
    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
                .subscribe(data -> log.info("onNext: {}", data));
        doTask("task2")
                .subscribe(data -> log.info("onNext: {}", data));
        Thread.sleep(200L);
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("onNext: {}", data))
                // 스레드 이름 지정 가능, 데몬 스레드로 동작할지의 여부 지정
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter data = {}", data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map data = {}", data));
    }
}
