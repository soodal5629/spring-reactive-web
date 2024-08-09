package com.example.reactor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

/**
 * Mono 활용
 */
@Slf4j
public class MonoExample3 {
    public static void main(String[] args) {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(restTemplate.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<String>(headers), String.class)) // 데이터 발행
                .map(response -> { // 데이터 처리
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response.getBody().toString());
                        String dateTime = String.valueOf(jsonNode.get("datetime"));
                        return dateTime;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe( // 데이터 구독
                    data -> log.info("# emitted data: {}", data),
                    error -> {
                        log.error(error.getMessage());
                    },
                        () -> log.info("# emitted onComplete signal")

                );
    }
}
