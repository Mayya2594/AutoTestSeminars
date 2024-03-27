package org.max.seminar_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProducerTest {

    @Test
    void testProducer() {
        //given
        ConsumerTelegram consumerTelegram = new ConsumerTelegram();
        ConsumerViber consumerViber = new ConsumerViber();
        Producer producer = new Producer();
        producer.subscribe(consumerTelegram);
        producer.subscribe(consumerViber);
        //when
        producer.sendMessage("Message from producer1");
        producer.sendMessage("Message from producer2");
        //then
        Assertions.assertEquals(2, consumerTelegram.getCount());
        Assertions.assertEquals(2, consumerViber.getCount());
    }
}
