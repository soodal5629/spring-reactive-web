package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Context 특징
 * Context는 체인의 맨 아래에서부터 위로 전파됨
 * 따라서 Operator 체인에서 Context read 읽는 동작이 Context write 동작 밑에 있을 경우, write 된 값을 read 할 수 없다.
 */
@Slf4j
public class ContextFeatureExample2 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        String key2 = "name";
        Mono.deferContextual(ctx ->
                Mono.just("ID: " + ctx.get(key1))) // key1, key2 값 read 가능
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key2, "Kevin"))
                .transformDeferredContextual((mono, ctx) ->
                        mono.map(data -> data + ", " + ctx.getOrDefault(key2, "Tom"))) // key2 read 불가능
                .contextWrite(context -> context.put(key1, "itVillage"))
                .subscribe(data -> log.info("OnNext = {}", data));

        Thread.sleep(100L);
    }
}
