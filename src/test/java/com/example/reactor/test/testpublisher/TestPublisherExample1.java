package com.example.reactor.test.testpublisher;

import com.example.reactor.test.stepverifier.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * TestPublisher를 사용해서 서비스 로직의 메소드에 대한 Unit test를 실시하는 예제
 * - 정상 동작하는 TestPublisher
 * - next() 사용
 */
public class TestPublisherExample1 {
    @Test
    void divideByTwoTest() {
        TestPublisher<Integer> source = TestPublisher.create();
        // flux() -> TestPublisher 를 Flux 타입으로 변환
        StepVerifier.create(GeneralExample.divideByTwo(source.flux()))
                .expectSubscription()
                .then(() -> source.next(2, 4, 6, 8, 10))
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify(); // 테스트 시작
    }
}
