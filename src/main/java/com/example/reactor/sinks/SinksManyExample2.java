package com.example.reactor.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sink.Many 예제
 * multicast()를 사용해서 하나 이상의 Subscriber 에게 데이터를 emit 하는 예제
 */
@Slf4j
public class SinksManyExample2 {
    public static void main(String[] args) {
        // 하나 이상의 Subscriber 에게만 데이터를 emit 할 수 있다.
        // multicast: Hot Sequence 동작 방식 중,최초의 구독이 있어야 데이터가 emit 되는 Warm up 방식으로 동작함
        // => 따라서 첫번째 구독은 1, 2, 3 데이터를 모두 받고 두번째 구독은 그 시점 이후부터 emit 된 3만 구독한다.
        Sinks.Many<Integer> multicastSink = Sinks.many().multicast().onBackpressureBuffer();
        // Flux로 변환 -> 구독에 이용
        Flux<Integer> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        // 첫번째 구독 발생 -> 1, 2 데이터 emit 발생 => 1, 2, 3 모든 데이터 구독
        fluxView.subscribe(data -> log.info("Subscriber 1 subscriber data {}", data));
        // 두번째 구독 발생 -> 이후에 emit 된 데이터 3 구독 가능
        fluxView.subscribe(data -> log.info("Subscriber 2 subscriber data {}", data));
        
        multicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

    }
}
