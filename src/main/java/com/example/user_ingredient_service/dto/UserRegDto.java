package com.example.user_ingredient_service.dto;

import com.example.user_ingredient_service.annotation.user.ValidExistUserLogin;
import com.example.user_ingredient_service.annotation.user.ValidExistUserMail;
import com.example.user_ingredient_service.annotation.user.ValidUserLogin;
import com.example.user_ingredient_service.annotation.user.ValidUserPassword;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "UserRegistration",description = "User for registration")
public class UserRegDto {

    @Schema(description = "user login",example = "user1")
    @ValidUserLogin
    @ValidExistUserLogin
    @NotBlank(message = "Login must be filled.")
    @Length(min = 1,max = 50,message = "Login length must be between {min} and {max} characters.")
    private String login;

    @Schema(description = "user mail",example = "user_1@yopmail.com")
    @Email
    @ValidExistUserMail
    @NotBlank(message = "Mail must be filled.")
    @Length(min = 8,max = 50,message = "Mail length must be between {min} and {max} characters.")
    private String mail;

    @Schema(description = "user password",example = "P@ssword1")
    @ValidUserPassword
    @NotBlank(message = "Password must be filled.")
    @Length(min = 8,max = 20,message = "Password length must be between {min} and {max} characters.")
    private String password;

}
