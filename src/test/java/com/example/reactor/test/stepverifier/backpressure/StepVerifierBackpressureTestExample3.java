package com.example.reactor.test.stepverifier.backpressure;

import com.example.reactor.test.stepverifier.BackpressureExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Backpressure DROP 전략을 검증하는 예제
 * - expectError()를 사용하여 에러가 발생되었는지 검증
 * - verifyThenAssertThat()을 사용하여 검증 이후에 assertion method를 사용하여 에러가 발생해도 추가 검증 가능
 * - hasDiscardedElements()를 사용하여 discard된 데이터가 있는지 검증. OverflowException이 발생할 때 2가 discard됨
 * - hasDiscarded()를 사용하여 discard된 데이터가 무엇인지 검증. Backpressure DROP 전략은 Drop된 데이터가 discard된다.
 */
public class StepVerifierBackpressureTestExample3 {
    @Test
    void generateNumberTest() {
        StepVerifier
                .create(BackpressureExample.generateNumberByDropStrategy(), 1L)
                .thenConsumeWhile(n -> n >= 1)
                // drop전략이므로 에러가 발생하지 않고 데이터가 drop 및 폐기 됨 -> 정상 종료
                .expectComplete()
                .verifyThenAssertThat()
                .hasDiscardedElements()
                .hasDiscarded(2, 3, 4, 5, 6, 77, 78, 100);
                // 해당 코드 주석해제하면 에러 발생 -> hasDropped의 drop과 Backpressure drop의 의미가 다르기 때문
                //.hasDropped(3, 4, 5, 6, 98, 99, 100);
    }
}
