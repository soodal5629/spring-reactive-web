package com.example.reactor.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * Sink.One 예제
 * 2건의 데이터 emit 하는 예제
 */
@Slf4j
public class SinksOneExample2 {
    public static void main(String[] args) {
        // emit 된 데이터 중에서 단 하나의 데이터만 Subscriber에게 전달한다. 나머지 데이터는 drop -> Mono와 같은 역할
        Sinks.One<String> sinkOne = Sinks.one();
        // Mono로 변환 - Sink.One에서의 Subscriber 역할
        Mono<String> mono = sinkOne.asMono();

        // publisher 역할
        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST);
        // Sink.One은 단 한개의 데이터를 emit 할 수 있기 때문에 두번째 emit 한 데이터는 drop 된다.
        sinkOne.emitValue("Hi Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> log.info("Subscriber1 {}", data));
        mono.subscribe(data -> log.info("Subscriber2 {}", data));
    }
}
