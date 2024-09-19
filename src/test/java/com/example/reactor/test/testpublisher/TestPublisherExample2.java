package com.example.reactor.test.testpublisher;

import com.example.reactor.test.stepverifier.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * TestPublisher를 사용해서 서비스 로직의 메소드에 대한 Unit test를 실시하는 예제
 * - 정상 동작하는 TestPublisher
 * - next() 사용
 * - 에러 발생 여부 검증
 */
public class TestPublisherExample2 {
    @Test
    void divideByTwoTest() {
        TestPublisher<Integer> source = TestPublisher.create();
        StepVerifier.create(GeneralExample.occurError(source.flux()))
                .expectSubscription()
                .then(() -> source.next(2, 4, 6, 8, 10))
                .expectNext(1, 2, 3, 4)
                .expectError() // 마지막은 에러 발생할 것을 기대
                .verify(); // 테스트 시작
    }
}
