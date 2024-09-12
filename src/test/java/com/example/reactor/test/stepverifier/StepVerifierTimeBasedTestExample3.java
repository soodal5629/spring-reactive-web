package com.example.reactor.test.stepverifier;

import com.example.reactor.test.TimeBasedExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * 검증에 소요되는 시간을 제한하는 예제
 * - verify(Duration)을 통해 설정한 시간 내에 검증이 끝나는지를 확인할 수 있다.
 */
public class StepVerifierTimeBasedTestExample3 {
    @Test
    void getCOVID19CountTest() {
        StepVerifier
                .create(TimeBasedExample.getCOVID19Count(
                            Flux.interval(Duration.ofMinutes(1)).take(1)
                        )
                )
                .expectSubscription()
                .expectNextCount(11)
                .expectComplete()
                // 3초 내에 테스트가 완료되는지 검증 - 3초내에 끝나지 않으면 time out이 나며 에러 발생
                .verify(Duration.ofSeconds(3));
    }
}
