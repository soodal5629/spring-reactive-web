package com.example.reactor.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/**
 * create() operator 사용 예제
 * - 일반적으로 Publisher의 데이터 생성을 단일 스레드에서 진행한다. 멀티 스레드에서도 가능
 * 데이터 emit은 create operator 내부에서 가능
 * Backpressure 적용 가능
 */
@Slf4j
public class ProgrammaticCreateExample {
    public static void main(String[] args) {
        int tasks = 6;
        Flux
                // FluxSink: 데이터를 동기/비동기로 emit 하게 해 주며 operator 내에서만 이용 가능
                // create: emit 할 데이터를 일반적으로 단일 스레드에서 생성(멀티 스레드도 가능하긴 함)
                .create((FluxSink<String> sink) -> {
                    IntStream.range(1, tasks)
                            .forEach(n -> sink.next(doTask(n)));
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> log.info("# create(): {}", n))
                .publishOn(Schedulers.parallel())
                .map(result -> result + "success!")
                .doOnNext(n -> log.info("# map(): {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));
    }

    private static String doTask(int taskNum) {
        return "task "+ taskNum + " result";
    }
}
