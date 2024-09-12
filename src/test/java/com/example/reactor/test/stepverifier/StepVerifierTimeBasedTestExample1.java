package com.example.reactor.test.stepverifier;

import com.example.reactor.test.TimeBasedExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

/**
 * 실제 시간을 가상 시간으로 대체하는 테스트 예제
 * - advanceTimeBy(Duration)을 통해 특정 시간만큼 시간을 앞당긴다.
 */
public class StepVerifierTimeBasedTestExample1 {
    @Test
    void getCOVID19CountTest() {
        StepVerifier
                .withVirtualTime(() -> TimeBasedExample.getCOVID19Count(
                        // interval - 특정 시점까지 데이터의 emit을 지연시켜줌
                        Flux.interval(Duration.ofHours(12)).take(1)
                ))
                .expectSubscription()
                // advanceTimeBy - 시간을 앞당겨줌 (여기서는 12시간 앞당겨주므로 12시간 후에 emit 될 데이터들을 받아볼 수 있음)
                .then(() -> VirtualTimeScheduler.get().advanceTimeBy(Duration.ofHours(12)))
                .expectNextCount(11)
                .expectComplete()
                .verify();
    }
}
