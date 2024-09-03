package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Context 특징
 * 동일한 key에 대해서 여러번 write할 경우, 기존 키 값을 덮어씀
 */
@Slf4j
public class ContextFeatureExample3 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        Mono.deferContextual(ctx ->
                Mono.just("ID: " + ctx.get(key1)))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "itWorld"))
                .contextWrite(context -> context.put(key1, "itVillage"))
                .subscribe(data -> log.info("OnNext = {}", data));

        Thread.sleep(100L);
    }
}
