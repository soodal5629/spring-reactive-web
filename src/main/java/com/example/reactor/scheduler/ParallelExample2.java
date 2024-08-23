package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

/**
 * parallel()만 사용할 경우에는 병렬로 작업을 수행하지 않는다.
 * -> runOn()을 사용해서 Scheduler를 할당해주어야 병렬로 작업을 수행한다.
 */
@Slf4j
public class ParallelExample2 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15})
                // 얘만 쓰면 병렬로 처리되지 않고 메인 쓰레드에서 전부 처리된다.
                .parallel()
                .runOn(Schedulers.parallel()) // 스케쥴러 지정
                .subscribe(data -> log.info("onNext(): {}", data));
        Thread.sleep(1000L); // 메인 쓰레드가 비동기 작업이 완료될때까지 대기할 수 있도록
    }

}