package com.example.reactor.debugging.logoperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LogOperatorExample1 {
    public static Map<String, String> fruits = new HashMap<>();
    static {
        fruits.put("banana", "바나나");
        fruits.put("apple", "사과");
        fruits.put("pear", "배");
        fruits.put("grape", "포도");
    }
    public static void main(String[] args) {
        Flux.fromArray(new String [] {"BANANAS", "APPLES", "PEARS", "MELONS"})
                .log()
                .map(String::toLowerCase)
                .map(fruit -> fruit.substring(0, fruit.length()-1))
                .map(fruits::get)
                .subscribe(data -> log.info("onNext: {}", data), error -> log.error("error: ", error));
    }
}
