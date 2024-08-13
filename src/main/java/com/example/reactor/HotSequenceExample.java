package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
public class HotSequenceExample {
    public static void main(String[] args) throws InterruptedException {
        // fromStream -> java의 stream을 원본 데이터 소스로 전달 받음
        Flux<String> concertFlux = Flux.fromStream(Stream.of("Singer A", "Singer B","Singer C", "Singer D", "Singer E"))
                // delayElements -> 1초에 한번씩 데이터를 emit
                // share() 원본 Flux를 여러 Subscriber가 공유한다. 즉, cold sequence를 hot sequence로 변환해준다고 보면 됨
                .delayElements(Duration.ofSeconds(1)).share();
        concertFlux.subscribe(singer -> log.info("# Subscriber1 is watching {}'s song", singer));
        Thread.sleep(2500);
        concertFlux.subscribe(singer -> log.info("# Subscriber2 is watching {}'s song", singer));
        // cold sequence가 hot sequence로 변환될 때 메인 스레드 외에 다른 스레드가 생성됨
        Thread.sleep(3000); // 메인 스레드가 먼저 종료되지 않고 코드의 실행결과 모두 확인할 수 있도록
    }
}
