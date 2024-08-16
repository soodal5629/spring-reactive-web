package com.example.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
/**
 * Subscriber가 처리 가능한만큼의 request 개수를 조절하는 Backpressure 예제
 */
public class BackpressureExample2 {
    public static int count = 0;
    public static void main(String[] args) {
        Flux.range(1, 5) // data emit
                .doOnNext(data -> log.info("emit data = {}", data)) // upstream에서 emit한 데이터를 출력
                .doOnRequest(c -> log.info("request count = {}", c)) // subscriber에서 요청한 데이터의 개수를 출력
                // BaseSubscriber 를 통해 요청 데이터의 개수 조절
                .subscribe(new BaseSubscriber<Integer>() {
                    // 데이터의 요청 개수를 2로 지정
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }
                    // emit된 데이터를 전달 받음
                    @Override
                    protected void hookOnNext(Integer value) {
                        count++;
                        log.info("value = {}", value);
                        if(count == 2) {
                            try {
                                Thread.sleep(2000L);
                                // publisher에게 데이터의 요청 개수를 2로 지정
                                request(2);
                                count = 0;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
    }
}
