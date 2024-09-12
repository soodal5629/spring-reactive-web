package com.example.reactor.test.stepverifier;

import com.example.reactor.test.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * onError signal 발생 여부를 검증
 */
class StepVerifierGeneralTestExample4 {

    @Test
    public void sayHelloReactorTest() {
        Flux<Integer> source = Flux.just(2, 4, 6, 8, 10);
        StepVerifier
                .create(GeneralExample.occurError(source))
                .expectSubscription() // 구독이 발생하기를 기대
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectError()
                .verify(); // 검증 실행 및 테스트 트리거
    }
}