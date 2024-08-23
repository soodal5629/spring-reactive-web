
package com.example.reactor.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sink.Many 예제
 * replay()를 사용해서 이미 emit된 데이터 중에서 특정 개수의 최신 데이터만 전달하는 예제
 */
@Slf4j
public class SinksManyExample3 {
    public static void main(String[] args) {
        // 구독 이후, emit 된 데이터 중에서 최신 가장 데이터 2개만 replay 한다.
        Sinks.Many<Integer> multicastSink = Sinks.many().replay().limit(2);
        // Flux로 변환 -> 구독에 이용
        Flux<Integer> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber 1 subscriber data {}", data));
        fluxView.subscribe(data -> log.info("Subscriber 2 subscriber data {}", data));
    }
}