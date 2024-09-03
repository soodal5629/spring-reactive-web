package com.example.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.newBoundedElastic()을 적용
 */
@Slf4j
public class SchedulersNewBoundedElasticExample1 {
    public static void main(String[] args) throws InterruptedException {
        // newBoundedElastic는 Reactor에서 제공하는 default 스케쥴러 인스턴스를 사용하지 않고 새로운 Scheduler 인스턴스를 생성
        // 파라미터 1: 생성할 스레드의 갯수
        // 파라미터 2: 모든 스레드가 작업을 처리중일 경우, 큐에서 대기할 수 있는 작업의 갯수 설정
        Scheduler scheduler = Schedulers.newBoundedElastic(2, 2, "I/O-Thread");
        //Scheduler scheduler = Schedulers.boundedElastic();
        // subscribeOn 을 이용해서 위의 newBoundedElastic 에서 생성한 Scheduler 인스턴스를 지정
        Mono<Integer> mono = Mono.just(1).subscribeOn(scheduler);
        log.info("# Start");
        mono.subscribe(data -> {
                    log.info("subscribe 1 doing {}", data);
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("subscribe 1 done {}", data);
        });
        mono.subscribe(data -> {
            log.info("subscribe 2 doing {}", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 2 done {}", data);
        });
        // 이미 2개의 스레드가 위의 2개의 작업을 처리중이기 때문에 큐에서 대기하게 됨

        mono.subscribe(data -> log.info("subscribe 3 doing {}", data));
        mono.subscribe(data -> log.info("subscribe 4 doing {}", data));
        mono.subscribe(data -> log.info("subscribe 5 doing {}", data));
        mono.subscribe(data -> log.info("subscribe 6 doing {}", data));
        // 스레드가 아직 작업중이고 큐도 꽉 찼을 경우, subscribe 메소드를 한번 더 호출하면 Exception이 발생한다.
        //mono.subscribe(data -> log.info("subscribe 7 doing {}", data));

        // newBoundedElastic를 통해서 생성된 스레드는 데몬 스레드가 아닌 유저 스레드이기 때문에 일정 시간동안 종료되지 않는다
        // 작업이 끝나면 즉시 종료시키고 싶다면 dispose 메소드를 호출하면 됨
        Thread.sleep(4000L);
        scheduler.dispose();
    }
}
