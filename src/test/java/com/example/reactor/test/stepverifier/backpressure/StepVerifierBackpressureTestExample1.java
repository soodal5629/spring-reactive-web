package com.example.reactor.test.stepverifier.backpressure;

import com.example.reactor.test.stepverifier.BackpressureExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Backpressure 전략에 따라 Exception이 발생하는 예제
 * - request 데이터 개수보다 많은 데이터가 emit 되어 OverflowException이 발생
 * - OverflowException이 발생하게 된 데이터는 discard 됨
 * - 나머지 emit 된 데이터들은 Hooks.onNextDropped()에 의해 drop됨
 */
public class StepVerifierBackpressureTestExample1 {
    @Test
    void generateNumberTest() {
        StepVerifier
                // 두번째 파라미터는 upstream에 요청하는 데이터의 개수(여기서는 1개 지정)
                .create(BackpressureExample.generateNumberByErrorStrategy(), 1L)
                .thenConsumeWhile(num -> num >= 1) // emit된 데이터 중에서 조건에 맞는 데이터만 소비한다.
                .verifyComplete();
    }
}
