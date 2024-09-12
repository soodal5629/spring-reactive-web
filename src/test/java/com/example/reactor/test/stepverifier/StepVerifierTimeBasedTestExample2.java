package com.example.reactor.test.stepverifier;

import com.example.reactor.test.TimeBasedExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

/**
 * 실제 시간을 가상 시간으로 대체하는 테스트 예제
 * - thenAwait(Duration)을 통해 특정 시간만큼 가상으로 기다린다.
 * - 즉, 특성 시간을 기다린 것처럼 시간을 당긴다.
 */
public class StepVerifierTimeBasedTestExample2 {
    @Test
    void getCOVID19CountTest() {
        StepVerifier
                .withVirtualTime(() -> TimeBasedExample.getCOVID19Count(
                        // interval - 특정 시점까지 데이터의 emit을 지연시켜줌
                        Flux.interval(Duration.ofHours(12)).take(1)
                ))
                .expectSubscription()
                // thenAwait - 특정 시간만큼 기다려주는 효과
                .thenAwait(Duration.ofHours(12))
                .expectNextCount(11)
                .expectComplete()
                .verify();
    }
}
