package com.example.user_ingredient_service.service_unit;

import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.dto.TokenResponse;
import com.example.user_ingredient_service.dto.UserRegDto;
import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.generator.SecondaryGeneratorUtil;
import com.example.user_ingredient_service.generator.UserDtoGeneratorUtil;
import com.example.user_ingredient_service.generator.UserGeneratorUtil;
import com.example.user_ingredient_service.mapper.TokenMapper;
import com.example.user_ingredient_service.mapper.UserMapper;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.security.JwtTokenUtilize;
import com.example.user_ingredient_service.service.impl.RefreshTokenServiceImpl;
import com.example.user_ingredient_service.service.impl.UserIngredientServiceImpl;
import com.example.user_ingredient_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserIngredientServiceImpl userIngredientService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RefreshTokenServiceImpl refreshTokenService;
    @Mock
    private JwtTokenUtilize jwtTokenUtilize;
    @Mock
    private TokenMapper tokenMapper;
    private User user;
    private UserRegDto userRegDto;

    @BeforeEach
    void setup() {
        user = UserGeneratorUtil.createValidUser();
        user.setId(1L);
        userRegDto = UserDtoGeneratorUtil.createValidUserForReg();
    }

    @Test
    void testRegistrationShouldSaveUser() {
        RefreshToken token = new RefreshToken();
        String accessToken = SecondaryGeneratorUtil.generateRndStr();
        String refreshToken = SecondaryGeneratorUtil.generateRndStr();
        token.setToken(refreshToken);
        given(userMapper.buildUser(userRegDto)).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        given(refreshTokenService.createRefreshToken(user)).willReturn(token);
        given(jwtTokenUtilize.generateAccessToken(user.getLogin())).willReturn(accessToken);
        given(tokenMapper.mapTokens(accessToken,refreshToken)).willReturn(new TokenResponse(accessToken,refreshToken));
        Assertions.assertDoesNotThrow(()->userService.registration(userRegDto));
    }

    @Test
    void testGetUserByMaxGoldShouldReturnUsers() {
        Page<HighScoreTable> highScoreTablesByMaxGold = new PageImpl<>(
                List.of(
                        new HighScoreTable("test", 1L, 2L),
                        new HighScoreTable("test", 2L, 1L)));
        given(userRepository.findUsersByMaxGold(isA(Pageable.class))).willReturn(highScoreTablesByMaxGold);
        Page<HighScoreTable> usersByMaxGold = userService.getUsersByMaxGold(Pageable.unpaged());
        Assertions.assertNotNull(usersByMaxGold);
        Assertions.assertEquals(2,usersByMaxGold.getContent().size());
    }

    @Test
    void testGetUsersByMaxUserIngredientCountShouldReturnUsers() {
        Page<HighScoreTable> highScoreTablesByCount = new PageImpl<>(
                List.of(
                        new HighScoreTable("test",5L,1L),
                        new HighScoreTable("test",4L,2L)));
        given(userRepository.findUsersByMaxUserIngredientCount(isA(Pageable.class))).willReturn(highScoreTablesByCount);
        Page<HighScoreTable> usersByMaxUserIngredientCount = userService.getUsersByMaxUserIngredientCount(Pageable.unpaged());
        Assertions.assertNotNull(usersByMaxUserIngredientCount);
        Assertions.assertEquals(2,usersByMaxUserIngredientCount.getContent().size());
    }

}
