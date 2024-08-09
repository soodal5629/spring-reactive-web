package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoExample2 {
    public static void main(String[] args) {
        Mono.empty()
                .subscribe(
                        data -> log.info("# emitted data: {}", data), // 데이터 보내기
                        error -> {}, // 에러가 발생할 경우, 에러를 전달받아서 처리
                        () -> log.info("# emitted onComplete signal") // 모든 데이터 전송 후, onComplete signal을 전달받아서 처리
                );
    }
}
