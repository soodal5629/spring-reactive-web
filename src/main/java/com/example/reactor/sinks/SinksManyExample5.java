
package com.example.reactor.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sink.Many 예제
 */
@Slf4j
public class SinksManyExample5 {
    public static void main(String[] args) {
        // 구독 시점과 상관없이 emit 된 모든 데이터를 replay 한다.
        Sinks.Many<Integer> multicastSink = Sinks.many().replay().all();
        // Flux로 변환 -> 구독에 이용
        Flux<Integer> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        fluxView.subscribe(data -> log.info("Subscriber 0 subscriber data {}", data));
        multicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber 1 subscriber data {}", data));
        fluxView.subscribe(data -> log.info("Subscriber 2 subscriber data {}", data));
    }
}