package com.example.reactor.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * Sink.Many 예제
 * unicast()를 사용해서 단 하나의 Subscriber 에게만 데이터를 emit 하는 예제
 */
@Slf4j
public class SinksManyExample1 {
    public static void main(String[] args) {
        // 단 하나의 Subscriber 에게만 데이터를 emit 할 수 있다.
        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        // Flux로 변환 -> 구독에 이용
        Flux<Integer> fluxView = unicastSink.asFlux();

        unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber 1 subscriber data {}", data));

        unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber 2 {}", data));
    }
}
