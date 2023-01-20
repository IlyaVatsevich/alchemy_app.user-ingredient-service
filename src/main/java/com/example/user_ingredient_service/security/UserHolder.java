package com.example.user_ingredient_service.security;

import com.example.user_ingredient_service.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder  {

    public UserDetailsImpl getUserDetails(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser() {
        return getUserDetails().getUser();
    }

}
