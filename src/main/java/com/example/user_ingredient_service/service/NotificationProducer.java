package com.example.user_ingredient_service.service;

import com.example.user_ingredient_service.dto.IngredientForNotification;

public interface NotificationProducer {

    void sendNotificationAboutNewIngredient(IngredientForNotification ingredientNotification);

}
