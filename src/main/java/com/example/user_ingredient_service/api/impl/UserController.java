package com.example.user_ingredient_service.api.impl;


import com.example.user_ingredient_service.api.UserApiDescription;
import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.dto.TokenRequest;
import com.example.user_ingredient_service.dto.TokenResponse;
import com.example.user_ingredient_service.dto.UserLogDto;
import com.example.user_ingredient_service.dto.UserRegDto;
import com.example.user_ingredient_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApiDescription {

    private final UserService userService;

    @Override
    public ResponseEntity<TokenResponse> registration(UserRegDto userRegDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registration(userRegDto));
    }

    @Override
    public ResponseEntity<Page<HighScoreTable>> getUsersByMaxGold(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByMaxGold(pageable));
    }

    @Override
    public ResponseEntity<TokenResponse> login(UserLogDto userLogDto) {
        return ResponseEntity.ok(userService.login(userLogDto));
    }

    @Override
    public ResponseEntity<Page<HighScoreTable>> getUsersByMaxCount(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByMaxUserIngredientCount(pageable));
    }

    @Override
    public ResponseEntity<TokenResponse> getNewAccessAndRefreshTokenByRefreshToken(TokenRequest tokenRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getNewAccessAndRefreshTokenByRefreshToken(tokenRequest));
    }
}
