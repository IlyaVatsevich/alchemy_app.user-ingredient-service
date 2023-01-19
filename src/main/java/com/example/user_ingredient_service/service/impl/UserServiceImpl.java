package com.example.user_ingredient_service.service.impl;

import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.dto.TokenRequest;
import com.example.user_ingredient_service.dto.TokenResponse;
import com.example.user_ingredient_service.dto.UserLogDto;
import com.example.user_ingredient_service.dto.UserRegDto;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.mapper.UserMapper;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.service.UserIngredientService;
import com.example.user_ingredient_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserIngredientService userIngredientService;

    @Override
    @Transactional
    public TokenResponse registration(UserRegDto userRegDto) {
        User newUser = userMapper.buildUser(userRegDto);
        userIngredientService.initializeUserIngredient(newUser);
        newUser = userRepository.save(newUser);
        log.info("New user with id - {} , saved successfully.",newUser.getId());
        return null;
    }

    @Override
    @Transactional
    public TokenResponse login(UserLogDto userLogDto) {
       return null;
    }

    @Override
    public Page<HighScoreTable> getUsersByMaxGold(Pageable pageable) {
        return userRepository.findUsersByMaxGold(pageable);
    }

    @Override
    public Page<HighScoreTable> getUsersByMaxUserIngredientCount(Pageable pageable) {
        return userRepository.findUsersByMaxUserIngredientCount(pageable);
    }

    @Override
    @Transactional
    public TokenResponse getNewAccessAndRefreshTokenByRefreshToken(TokenRequest tokenRequest) {
        return null;
    }
}
