package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * Context API 예제 코드
 * putAll(ContextView) 사용
 */
@Slf4j
public class ContextAPIExample2 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        String key2 = "name";
        String key3 = "country";
        Mono.deferContextual(ctx ->
                Mono.just("ID: " + ctx.get(key1) + ", Name: " + ctx.get(key2) + ", Country: " + ctx.get(key3))
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.putAll(Context.of(key2, "Kevin", key3, "Korea").readOnly()))
                .contextWrite(context -> context.put(key1, "itVillage"))
                .subscribe(data -> log.info("onNext: {}", data));
        Thread.sleep(100L);
    }
}
