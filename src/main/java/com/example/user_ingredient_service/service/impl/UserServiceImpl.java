package com.example.user_ingredient_service.service.impl;

import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.dto.TokenRequest;
import com.example.user_ingredient_service.dto.TokenResponse;
import com.example.user_ingredient_service.dto.UserLogDto;
import com.example.user_ingredient_service.dto.UserRegDto;
import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.exception.EntityNotExistException;
import com.example.user_ingredient_service.exception.PasswordNotCorrectException;
import com.example.user_ingredient_service.mapper.TokenMapper;
import com.example.user_ingredient_service.mapper.UserMapper;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.security.JwtTokenUtilize;
import com.example.user_ingredient_service.security.UserDetailsImpl;
import com.example.user_ingredient_service.service.RefreshTokenService;
import com.example.user_ingredient_service.service.UserIngredientService;
import com.example.user_ingredient_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserIngredientService userIngredientService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtilize jwtTokenUtilize;
    private final TokenMapper tokenMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TokenResponse registration(UserRegDto userRegDto) {
        User newUser = userMapper.buildUser(userRegDto);
        userIngredientService.initializeUserIngredient(newUser);
        newUser = userRepository.save(newUser);
        log.info("New user with id - {} , saved successfully.",newUser.getId());
        return createTokenResponse(newUser);
    }

    @Override
    @Transactional
    public TokenResponse login(UserLogDto userLogDto) {
        UserDetails userDetails = loadUserByUsername(userLogDto.getLogin());
        if (!passwordEncoder.matches(userLogDto.getPassword(),userDetails.getPassword())) {
            throw new PasswordNotCorrectException("Password not correct.");
        }
        return createTokenResponse(((UserDetailsImpl) userDetails).getUser());
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
        RefreshToken oldRefreshToken = refreshTokenService.findByToken(tokenRequest.getRefreshToken());
        return createTokenResponse(oldRefreshToken.getUser());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).
                orElseThrow(() -> new EntityNotExistException("User with such login: " + username + ", not exist."));
        return new UserDetailsImpl(user);
    }

    private TokenResponse createTokenResponse(User user) {
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();
        String accessToken = jwtTokenUtilize.generateAccessToken(user.getLogin());
        return tokenMapper.mapTokens(accessToken,refreshToken);
    }
}
