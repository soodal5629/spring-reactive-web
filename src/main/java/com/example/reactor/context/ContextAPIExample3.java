package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * Context API 예제 코드
 * getOrDefault() 사용
 */
@Slf4j
public class ContextAPIExample3 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        String key2 = "name";
        Mono.deferContextual(ctx ->
                Mono.just("ID: " + ctx.get(key1) + ", Name: " + ctx.get(key2) + ", Job: " + ctx.getOrDefault("job", "Software Engineer"))
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(Context.of(key1, "itVillage", key2, "Kevin"))
                .subscribe(data -> log.info("onNext: {}", data));
        Thread.sleep(100L);
    }
}
