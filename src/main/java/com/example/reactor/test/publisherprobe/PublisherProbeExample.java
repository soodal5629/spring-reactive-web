package com.example.reactor.test.publisherprobe;

import reactor.core.publisher.Mono;

public class PublisherProbeExample {
    public static Mono<String> processWith(Mono<String> main, Mono<String> standby) {
        return main
                .flatMap(message -> Mono.just(message))
                .switchIfEmpty(standby);
    }

    public static Mono<String> useMainPower() {
        return Mono.empty();
    }

    public static Mono useStandbyPower() {
        return Mono.just("### use Standby Power");
    }
}
