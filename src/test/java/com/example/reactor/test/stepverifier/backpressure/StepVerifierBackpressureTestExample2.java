package com.example.reactor.test.stepverifier.backpressure;

import com.example.reactor.test.stepverifier.BackpressureExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Backpressure ERROR 전략을 검증하는 예제
 * - expectError()를 사용하여 에러가 발생되었는지 검증
 * - verifyThenAssertThat()을 사용하여 검증 이후에 assertion method를 사용하여 에러가 발생해도 추가 검증 가능
 * - hasDiscardedElements()를 사용하여 discard된 데이터가 있는지 검증. OverflowException이 발생할 때 2가 discard됨
 * - hasDiscarded()를 사용하여 discard된 데이터가 무엇인지 검증. OverflowException이 발생할 때 2가 discard됨
 * - hasDroppedElements()를 사용하여 Hooks.onNextDropped()으로 drop된 데이터가 있는지 검증
 * - hasDropped()를 사용하여 Hooks.onNextDropped()으로 drop된 데이터가 무엇인지 검증
 */
public class StepVerifierBackpressureTestExample2 {
    @Test
    void generateNumberTest() {
        StepVerifier
                .create(BackpressureExample.generateNumberByErrorStrategy(), 1L)
                .thenConsumeWhile(n -> n >= 1)
                .expectError()
                .verifyThenAssertThat()
                .hasDiscardedElements()
                .hasDiscarded(2)
                .hasDroppedElements()
                .hasDropped(3, 4, 5, 6, 98, 99, 100);
    }
}
