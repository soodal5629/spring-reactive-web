package com.example.reactor.debugging.logoperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

import java.util.HashMap;
import java.util.Map;

/**
 * log() operator와 debug mode를 같이 사용한 예제
 * log()는 에러 발생 시, stacktrace와 함께 traceback도 같이 출력함
 */
@Slf4j
public class LogOperatorExample2 {
    public static Map<String, String> fruits = new HashMap<>();
    static {
        fruits.put("banana", "바나나");
        fruits.put("apple", "사과");
        fruits.put("pear", "배");
        fruits.put("grape", "포도");
    }
    public static void main(String[] args) {
        Hooks.onOperatorDebug(); // debug mode
        Flux.fromArray(new String [] {"BANANAS", "APPLES", "PEARS", "MELONS"})
                .log()
                .map(String::toLowerCase)
                .log()
                .map(fruit -> fruit.substring(0, fruit.length()-1))
                .log()
                .map(fruits::get)
                .log()
                .subscribe(data -> log.info("onNext: {}", data), error -> log.error("error: ", error));
    }
}
