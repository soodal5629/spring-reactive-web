package com.example.reactor.test.stepverifier;

import com.example.reactor.test.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 1개 이상의 emit 된 데이터를 한꺼번에 검증
 */
class StepVerifierGeneralTestExample5 {

    @Test
    public void sayHelloReactorTest() {
        Flux<Integer> source = Flux.just(2, 4, 6, 8, 10);
        StepVerifier
                .create(GeneralExample.divideByTwo(source))
                .expectSubscription() // 구독이 발생하기를 기대
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify(); // 검증 실행 및 테스트 트리거
    }
}