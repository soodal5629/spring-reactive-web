package com.example.reactor.test.stepverifier;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

import java.time.Duration;

/**
 * expectNoEvent(Duration)으로 지정된 대기 시간동안 이벤트가 없음을 확인하는 예제
 */
public class StepVerifierTimeBasedTestExample4 {
    @Test
    void getCOVID19CountTest() {
        StepVerifier
                .withVirtualTime(() -> TimeBasedExample.getVoteCount(
                        // 1분에 1번씩 데이터 emit
                        Flux.interval(Duration.ofMinutes(1))
                        )
                )
                .expectSubscription()
                // 해당 가상 시간동안 어떤 이벤트도 발생하지 않았음을 기대/검증
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNext(Tuples.of("중구", 15400))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }
}
