package com.example.user_ingredient_service.mapper;

import com.example.user_ingredient_service.dto.UserRegDto;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.entity.UserRole;
import com.example.user_ingredient_service.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@DependsOn("liquibase")
public class UserMapper {

    @Value("${user.start-gold}")
    private int startGold;
    private final UserRoleRepository userRoleRepository;
    private UserRole userRole;

    @PostConstruct
    void setUserRoleForRegisteredUsers() {
        userRole = userRoleRepository.getUserRoleUser();
    }

    public User buildUser(UserRegDto userRegDto) {
        return User.builder().withGold(startGold)
                .withLogin(userRegDto.getLogin())
                .withMail(userRegDto.getMail())
                .withPassword(userRegDto.getPassword())
                .withRoles(Set.of(userRole))
                .build();
    }

}
