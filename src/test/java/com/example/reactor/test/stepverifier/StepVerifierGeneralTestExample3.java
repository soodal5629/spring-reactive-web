package com.example.reactor.test.stepverifier;

import com.example.reactor.test.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * verifyComplete()를 사용하여 검증 실행 및 기대값으로 onComplete signal이 맞는지 검증하는 예제
 * as(description)를 사용해서 실패한 expectXXX()에게 description 을 지정할 수 있음
 */
class StepVerifierGeneralTestExample3 {

    @Test
    public void sayHelloReactorTest() {
        StepVerifier
                .create(GeneralExample.sayHelloReactor())
                .expectSubscription() // 구독이 발생하기를 기대
                .as("### expect subscription")
                .expectNext("Hi")
                .as("### expect Hi")
                .expectNext("Reactor")
                .as("### expect Reactor")
                .verifyComplete(); // 검증 실행 및 테스트 트리거
    }
}