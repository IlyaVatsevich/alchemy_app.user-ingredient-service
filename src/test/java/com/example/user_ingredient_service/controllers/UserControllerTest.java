package com.example.user_ingredient_service.controllers;

import com.example.user_ingredient_service.dto.TokenRequest;
import com.example.user_ingredient_service.dto.UserRegDto;
import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.repository.RefreshTokenRepository;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.test_container.Neo4jConfiguredContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.example.user_ingredient_service.generator.UserDtoGeneratorUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active = test")
@AutoConfigureMockMvc
@Transactional
class UserControllerTest implements Neo4jConfiguredContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Long validUserId;

    private final String userDetailsName = "admin_1";

    @BeforeEach
    void setup() {
        List<User> all = userRepository.findAll();
        validUserId = all.get(0).getId();
    }

    @Test
    void testRegistrationShouldRegisterUser() throws Exception {
        UserRegDto validUserForReg = createValidUserForReg();
        mockMvc.perform(post("/api/v1/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(validUserForReg)))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegistrationShouldThrowCauseOfInvalidMail() throws Exception {
        UserRegDto userWithInvalidMail = createUserWithInvalidMail();
        mockMvc.perform(post("/api/v1/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(userWithInvalidMail)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrationShouldThrowCauseOfInvalidPassword() throws Exception {
        UserRegDto userWithInvalidPassword = createUserWithInvalidPassword();
        mockMvc.perform(post("/api/v1/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(userWithInvalidPassword)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testRegistrationShouldThrowCauseOfUserAlreadyLogged() throws Exception {
        mockMvc.perform(post("/api/v1/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createValidLogUser())))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUserLoginShouldLoginUser() throws Exception {
        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createValidLogUser())))
                .andExpect(status().isOk());
    }

    @Test
    void testUserLoginShouldThrowCauseOfUserWithSuchLoginNotExist() throws Exception {
        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createNotExistingLogUser())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUserLoginShouldThrowCauseOfUserWithInvalidPassword() throws Exception {
        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createExistingUserWithInvalidPassword())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testUserLoginShouldThrowCauseUserAlreadyLogged() throws Exception {
        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createValidLogUser())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testGetUsersByMaxGoldShouldReturnUsers() throws Exception {
        mockMvc.perform(get("/api/v1/user/high_score/gold")
                        .content(objectToJson(Pageable.unpaged())))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserByMaxGoldShouldThrowCauseOfAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/v1/user/high_score/gold")
                        .content(objectToJson(Pageable.unpaged())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testGetUserIngredientShouldReturnUserIngredient() throws Exception {
        mockMvc.perform(get("/api/v1/user_ingredient")
                        .content(objectToJson(Pageable.unpaged())))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserIngredientShouldThrowCauseOfAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/v1/user/user_ingredient")
                        .content(objectToJson(Pageable.unpaged())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetNewAccessTokenByRefreshTokenShouldReturnNewAccessToken() throws Exception {
        User user = userRepository.findById(validUserId).get();
        RefreshToken refreshToken = RefreshToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withUser(user)
                .withExpiryDate(LocalDateTime.now().plus(1000000, ChronoUnit.MINUTES))
                .build();
        TokenRequest tokenRequest = TokenRequest.builder().withRefreshToken(refreshToken.getToken()).build();
        refreshTokenRepository.save(refreshToken);
        mockMvc.perform(post("/api/v1/user/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(tokenRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetNewAccessTokenByRefreshTokenShouldThrowCauseTokenIsExpired() throws Exception {
        User user = userRepository.findById(validUserId).get();
        RefreshToken refreshToken = RefreshToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withUser(user)
                .withExpiryDate(LocalDateTime.now())
                .build();
        TokenRequest tokenRequest = TokenRequest.builder().withRefreshToken(refreshToken.getToken()).build();
        refreshTokenRepository.save(refreshToken);
        mockMvc.perform(post("/api/v1/user/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(tokenRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetNewAccessTokenByRefreshTokenShouldThrowCauseTokenIsInvalidFormat() throws Exception {
        TokenRequest tokenRequest = TokenRequest.builder().withRefreshToken("123sdaer123asadf").build();
        mockMvc.perform(post("/api/v1/user/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(tokenRequest)))
                .andExpect(status().isBadRequest());
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
