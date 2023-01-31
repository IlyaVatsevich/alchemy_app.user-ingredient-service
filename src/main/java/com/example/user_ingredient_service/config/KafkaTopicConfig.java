package com.example.user_ingredient_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.name}")
    private String kafkaTopic;

    @Value("${kafka.topic.partitions}")
    private Integer kafkaCountPartitions;

    @Value("${kafka.topic.replicas}")
    private Integer kafkaCountReplicas;

    @Bean
    public NewTopic kafkaTopic() {
        return TopicBuilder
                .name(kafkaTopic)
                .partitions(kafkaCountPartitions)
                .replicas(kafkaCountReplicas)
                .build();
    }

}
