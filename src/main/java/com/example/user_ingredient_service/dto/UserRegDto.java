package com.example.user_ingredient_service.dto;

import com.example.user_ingredient_service.annotation.user.ValidExistUserLogin;
import com.example.user_ingredient_service.annotation.user.ValidExistUserMail;
import com.example.user_ingredient_service.annotation.user.ValidUserLogin;
import com.example.user_ingredient_service.annotation.user.ValidUserPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class UserRegDto {

    @ValidUserLogin
    @ValidExistUserLogin
    @NotBlank(message = "Login must be filled.")
    @Length(min = 1,max = 50,message = "Login length must be between {min} and {max} characters.")
    private String login;

    @Email
    @ValidExistUserMail
    @NotBlank(message = "Mail must be filled.")
    @Length(min = 8,max = 50,message = "Mail length must be between {min} and {max} characters.")
    private String mail;

    @ValidUserPassword
    @NotBlank(message = "Password must be filled.")
    @Length(min = 8,max = 20,message = "Password length must be between {min} and {max} characters.")
    private String password;

}