package com.example.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Context 활용 예제
 * 직교성(어플리케이션 실행에 영향을 주지 않는 정보 ex: 시큐리티 토큰)을 가지는 정보를 표현할 때 주로 사용됨
 */
@Slf4j
public class ContextRealExample {
    public static final String HEADER_NAME_AUTH_TOKEN = "authToken";
    public static void main(String[] args) {
        Mono<String> mono = postBook(Mono.just(
                new Book("abcd-1111-3533-1234", "Reactor's Bible", "Kevin"))
        )
                .contextWrite(Context.of(HEADER_NAME_AUTH_TOKEN, "Security Token"));
        mono.subscribe(data -> log.info("onNext = {}", data));
    }

    private static Mono<String> postBook(Mono<Book> book) {
        // zip: 파라미터로 전달된 두 Mono를 튜플 자료구조를 이용하여 합침
        return Mono.zip(book, Mono.deferContextual(ctx -> Mono.just(ctx.get(HEADER_NAME_AUTH_TOKEN))))
                .flatMap(tuple -> Mono.just(tuple)) // 외부 API 서버로 HTTP POST request 전송한다고 가정
                .flatMap(tuple -> {
                    // getT1: 튜플의 첫번째 데이터(여기서는 전달 받은 Book), T2: 튜플의 두번째 데이터(여기서는 Context에 저장된 토큰 정보)
                    String response = "POST the book(" + tuple.getT1().getBookName() + ", " + tuple.getT1().getAuthor() + ") with token: " + tuple.getT2();
                    return Mono.just(response); // HTTP response를 수신했다고 가정
                });
    }
}
