package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Context 개념 설명 예제 코드
 * - contextWrite() 으로 Context에 값을 쓸 수 있고, ContextView.get()을 통해 Context에 저장된 값을 read 할 수 있음
 * - ContextView는 deferContext() 또는 transformDeferredContextual()을 통해 제공됨
 */
@Slf4j
public class ContextIntroduceExample1 {
    public static void main(String[] args) throws InterruptedException {
        String key = "message";
        Mono<String> mono = Mono.deferContextual(ctx ->
                Mono.just("Hello " + ctx.get(key)).doOnNext(data -> log.info("doOnNext: {}", data))
                )
                        .subscribeOn(Schedulers.boundedElastic())
                        .publishOn(Schedulers.parallel())
                        .transformDeferredContextual((mono2, ctx) -> mono2.map(data -> data + " " + ctx.get(key)))
                        .contextWrite(context -> context.put(key, "Reactor"));
        mono.subscribe(data -> log.info("onNext: {}", data));
        Thread.sleep(100L);
    }
}
