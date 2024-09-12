package com.example.reactor.test.stepverifier;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class BackpressureExample {
    public static Flux<Integer> generateNumberByErrorStrategy() {
        return Flux.create(emitter -> {
            for (int i = 1; i <= 100; i++) {
                // data emit
                emitter.next(i);
            }
            emitter.complete();
            // backpressure 전략 중 error 전략
        }, FluxSink.OverflowStrategy.ERROR);
    }

    public static Flux<Integer> generateNumberByDropStrategy() {
        return Flux.create(emitter -> {
            for (int i = 1; i <= 100; i++) {
                emitter.next(i);
            }
            emitter.complete();
            // backpressure 전략 중 drop 전략
        }, FluxSink.OverflowStrategy.DROP);
    }
}
