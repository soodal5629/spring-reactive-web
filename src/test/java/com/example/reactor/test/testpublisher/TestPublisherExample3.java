package com.example.reactor.test.testpublisher;

import com.example.reactor.test.stepverifier.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * TestPublisher를 사용해서 서비스 로직의 메소드에 대한 Unit test를 실시하는 예제
 * - 정상 동작하는 TestPublisher
 * - emit() 사용
 */
public class TestPublisherExample3 {
    @Test
    void takeNumberTest() {
        TestPublisher<Integer> source = TestPublisher.create();
        StepVerifier.create(source.flux().log())
                .expectSubscription()
                // emit()이 아닌 next()를 사용할 경우 테스트가 끝나지 않음
                .then(() -> source.emit(1, 2, 3))
                .expectNext(1, 2, 3)
                .expectComplete()
                .verify(); // 테스트 시작
    }
}
