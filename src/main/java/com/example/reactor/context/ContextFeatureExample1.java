package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * Context 특징
 * Context는 각각의 구독을 통해 Reactor Sequence에 연결 되며 체인의 각 연산자가 연결된 Context에 접근할 수 있어야 한다.
 */
@Slf4j
public class ContextFeatureExample1 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        Mono<String> mono = Mono.deferContextual(ctx ->
                Mono.just("ID: " + ctx.get(key1)))
                .publishOn(Schedulers.parallel());
        mono.contextWrite(context -> context.put(key1, "itVillage"))
                .subscribe(data -> log.info("subscriber 1 data = {}", data));
        mono.contextWrite(context -> context.put(key1, "itWorld"))
                .subscribe(data -> log.info("subscriber 2 data = {}", data));
        Thread.sleep(100L);
    }
}
