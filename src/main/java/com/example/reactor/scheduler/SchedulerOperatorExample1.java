package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Sequence의 Operator 체인에서 최초의 스레드는 subscriber()가 호출되는 scope에 있는 스레드이다.
 */
@Slf4j
public class SchedulerOperatorExample1 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer [] {1, 3, 5, 7})
                .filter(data -> data > 3)
                .map(data -> data * 10)
                .subscribe(data -> log.info("data = {}", data));
    }
}
