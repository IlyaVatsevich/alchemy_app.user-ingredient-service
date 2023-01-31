package com.example.user_ingredient_service.service.impl;

import com.example.user_ingredient_service.dto.IngredientForNotification;
import com.example.user_ingredient_service.dto.IngredientNotification;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.service.NotificationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducerImpl implements NotificationProducer {

    private final KafkaTemplate<String, IngredientNotification> kafkaTemplate;
    private final NewTopic newTopic;
    private final UserRepository userRepository;

    @Override
    public void sendNotificationAboutNewIngredient(IngredientForNotification ingredientForNotification) {
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user ->  {
            IngredientNotification ingredientNotification = buildIngredientNotification(ingredientForNotification, user.getMail());
            CompletableFuture<SendResult<String, IngredientNotification>>
                    sendResultCompletableFuture = kafkaTemplate.send(newTopic.name(),UUID.randomUUID().toString(),ingredientNotification);
            sendResultCompletableFuture.whenComplete((ingredientNotificationSendResult, throwable) -> {
                if (throwable != null) {
                    log.error("Notification about ingredient - {}, was not send, to user - {}. Exception - {}.",
                            ingredientForNotification.getName(),throwable.getMessage(),ingredientNotification.getMail());
                }
            });
        });
        log.info("Notification about ingredient - {}, sent successfully.",ingredientForNotification.getName());
    }

    private IngredientNotification buildIngredientNotification(IngredientForNotification ingredient,String mail) {
        return IngredientNotification.builder()
                .withIngredient(ingredient)
                .withMail(mail)
                .build();
    }

}
