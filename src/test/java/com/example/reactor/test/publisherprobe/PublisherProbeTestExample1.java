package com.example.reactor.test.publisherprobe;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

public class PublisherProbeTestExample1 {
    @Test
    void publisherProbeTest() {
        PublisherProbe probe = PublisherProbe.of(PublisherProbeExample.useStandbyPower());
        StepVerifier
                .create(PublisherProbeExample.processWith(PublisherProbeExample.useMainPower(), probe.mono()))
                .expectNextCount(1)
                //.expectNext("### use Standby Power")
                .verifyComplete();

        probe.assertWasSubscribed();
        probe.assertWasRequested();
        probe.assertWasNotCancelled();
    }
}
