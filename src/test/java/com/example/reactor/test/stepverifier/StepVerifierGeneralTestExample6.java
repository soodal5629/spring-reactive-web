package com.example.reactor.test.stepverifier;

import com.example.reactor.test.GeneralExample;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

/**
 * onNext signal을 통해 emit된 데이터의 개수를 검증하는 예제
 * 검증에 실패할 경우에는 StepVerifierOptions에서 지정한 Scenario Name이 표시됨
 */
class StepVerifierGeneralTestExample6 {

    @Test
    public void sayHelloReactorTest() {
        Flux<Integer> source = Flux.range(0, 1000);
        StepVerifier
                .create(GeneralExample.takeNumber(source, 500),
                        // 시나리오 이름을 지정 -> 테스트가 실패할 경우 시나리오 이름을 로그로 출력
                        StepVerifierOptions.create().scenarioName("Verify from 0 to 499"))
                .expectSubscription() // 구독이 발생하기를 기대
                .expectNext(0) // 1st emitted data
                .expectNextCount(498) // 첫번째 이후 emit 되는 데이터의 개수
                .expectNext(500) // last emitted data
                .expectComplete()
                .verify(); // 검증 실행 및 테스트 트리거
    }
}