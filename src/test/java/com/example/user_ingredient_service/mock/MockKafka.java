package com.example.user_ingredient_service.mock;

import com.example.user_ingredient_service.service.NotificationProducer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.mock.mockito.MockBean;

public class MockKafka {

    @MockBean
    private NotificationProducer notificationProducer;

    @MockBean
    private KafkaProperties kafkaProperties;
}
