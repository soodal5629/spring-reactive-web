package com.example.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
/**
 * Unbounded reqeust일 경우, Downstream에 Backpressure Buffer OLDEST 전략을 사용하는 예제
 * - Downstream 으로 전달 할 데이터가 버퍼에 가득 찰 경우,
 *   버퍼 안에 있는 데이터 중에서 가장 먼저(오래된) 버퍼로 들어온 데이터부터 drop 시키는 전략
 */
public class BackpressureStrategyBufferDropOldestExample {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(300L)) // 0.3초에 한번씩 숫자 0부터 1씩 증가하며 데이터 emit
                .doOnNext(data -> log.info("# emitted by original Flux: {}", data)) // emit 된 데이터 확인
                // 버퍼 전략 적용
                .onBackpressureBuffer(2, // 버퍼 사이즈 2로 설정
                        // Overflow 됐을때 drop 된 데이터 확인
                        dropped -> log.info("# Overflow  & dropped: {}", dropped),
                        BufferOverflowStrategy.DROP_OLDEST)
                // 버퍼에서 emit 되는 데이터를 출력
                .doOnNext(data -> log.info("# emitted by Buffer: {}", data))
                // 3번째 파라미터는 prefetch는 추가되는 스레드 앞에서 일종의 버퍼 역할을 함
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                            try {
                                Thread.sleep(1000L);
                                log.info("value - {}", data);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                },
                error -> log.error("error class ={}, e.message = {}", error.getClass(), error.getMessage()));
        Thread.sleep(3000L);
    }
}
