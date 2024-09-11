package com.example.reactor.debugging.logoperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

/**
 * log() operator Custom Category 사용 예제
 */
@Slf4j
public class LogOperatorExample3 {
    public static Map<String, String> fruits = new HashMap<>();
    static {
        fruits.put("banana", "바나나");
        fruits.put("apple", "사과");
        fruits.put("pear", "배");
        fruits.put("grape", "포도");
    }
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new String [] {"BANANAS", "APPLES", "PEARS", "MELONS"})
                .subscribeOn(Schedulers.boundedElastic()) // 별도 스레드 지정
                .log("Fruit.Source") // 커스텀 카테고리 지정
                .publishOn(Schedulers.parallel()) // 별도 스레드 지정 -> 데이터 emit하는 스레드와 다름
                .map(String::toLowerCase)
                .log("Fruit.Lower")
                .map(fruit -> fruit.substring(0, fruit.length()-1))
                .log("Fruit.Substring")
                .map(fruits::get)
                .log("Fruit.Name")
                .subscribe(data -> log.info("onNext: {}", data), error -> log.error("error: ", error));
        Thread.sleep(100L);
    }
}
