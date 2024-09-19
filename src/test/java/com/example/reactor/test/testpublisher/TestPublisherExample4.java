package com.example.reactor.test.testpublisher;

import com.example.reactor.test.stepverifier.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * TestPublisher를 사용해서 서비스 로직의 메소드에 대한 Unit test를 실시하는 예제
 * - Reactive Streams 사양을 위반해도 Publisher가 정상 동작하게 함으로써 서비스 로직을 검증하는 예제
 */
public class TestPublisherExample4 {
    @Test
    void takeNumberTest() {
        // null emit 할 수 있도록(Reactive Streams 사양을 위반) 허용 -> emit되고 난 후에 가공 처리 중에 null 에러 발생
        //TestPublisher<Integer> source = TestPublisher.createNoncompliant(TestPublisher.Violation.ALLOW_NULL);
        // 위의 코드를 주석 처리하고 create() 부분을 주석해제하여 실행하면 null을 emit할 수 없다는 에러 발생(emit 되기 전에 에러 발생)
        TestPublisher<Integer> source = TestPublisher.create();
        StepVerifier.create(GeneralExample.divideByTwo(source.flux()))
                .expectSubscription()
                .then(() -> source.next(2, 4, 6, 8, null))
                .expectNext(1, 2, 3, 4)
                .expectComplete()
                .verify(); // 테스트 시작
    }
}
