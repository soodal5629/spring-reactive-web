package com.example.reactor.test.stepverifier;

import com.example.reactor.test.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * StepVerifier를 사용하여 emit된 n개의 데이터를 검증하는 예제
 */
class StepVerifierGeneralTestExample2 {

    @Test
    public void sayHelloReactorTest() {
        StepVerifier
                .create(GeneralExample.sayHelloReactor())
                .expectSubscription() // 구독이 발생하기를 기대
                .expectNext("Hello")
                .expectNext("Reactor")
                .expectComplete() // onComplete Signal 검증
                .verify(); // 검증 실행 및 테스트 트리거
    }
}