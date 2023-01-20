package com.example.user_ingredient_service.service.impl;

import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.mapper.RefreshTokenMapper;
import com.example.user_ingredient_service.repository.RefreshTokenRepository;
import com.example.user_ingredient_service.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public RefreshToken createRefreshToken(User user) {
        Long userId = user.getId();
        Optional<RefreshToken> refreshTokenByUser = refreshTokenRepository.findByUserId(userId);
        refreshTokenByUser.ifPresent(refreshTokenRepository::delete);
        return refreshTokenRepository.save(refreshTokenMapper.buildRefreshToken(user));
    }

    @Override
    public RefreshToken findByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Token not exist."));
        verifyExpiration(refreshToken);
        return refreshToken;
    }

    private void verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new CredentialsExpiredException("Token is expired.");
        }
    }
}
