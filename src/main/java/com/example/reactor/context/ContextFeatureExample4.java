package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Context 특징
 * inner Sequence 내부에서는 외부 Context에 저장된 데이터를 read할 수 있음
 * inner Sequence 내부에서 Context에 저장된 데이터는 외부에서 읽을 수 없음
 */
@Slf4j
public class ContextFeatureExample4 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        Mono.just("Kevin")
                // flatMap 내부: inner Sequence
                .flatMap(name -> Mono.deferContextual(ctx ->
                        Mono.just(ctx.get(key1) + ", " + name)
                                .transformDeferredContextual((mono, innerCtx) -> mono.map(data -> data + ", " + innerCtx.get("job")))
                                .contextWrite(context -> context.put("job", "Software Engineer"))
                        )
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "itVillage"))
                .subscribe(data -> log.info("onNext = {}", data));

        Thread.sleep(100L);
    }
}
