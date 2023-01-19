package com.example.user_ingredient_service.service;

import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.dto.TokenRequest;
import com.example.user_ingredient_service.dto.TokenResponse;
import com.example.user_ingredient_service.dto.UserLogDto;
import com.example.user_ingredient_service.dto.UserRegDto;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {

    TokenResponse registration(@Valid UserRegDto userRegDto);

    TokenResponse login(@Valid UserLogDto userLogDto);

    Page<HighScoreTable> getUsersByMaxGold(Pageable pageable);

    Page<HighScoreTable> getUsersByMaxUserIngredientCount(Pageable pageable);

    TokenResponse getNewAccessAndRefreshTokenByRefreshToken(@Valid TokenRequest tokenRequest);

}
