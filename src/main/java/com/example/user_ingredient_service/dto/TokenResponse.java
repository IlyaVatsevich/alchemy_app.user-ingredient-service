package com.example.user_ingredient_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
@Schema(description = "Token response",title = "TokenResponse")
public class TokenResponse {

    @Schema(example ="eyJhbGciOiJIUzUxMiJ9." +
            "eyJzdWIiOiJhZG1pbl8xIiwiaXNzIjoiSVciLCJpYXQiOjE2NzE0NTk3MDIsImV4cCI6MTY3MTQ2NjkwMn0" +
            ".w6WujN56t9HL_Z9OISLmsnjubzoeSM8kmno9v15q_VQeYBdd3gWr5JD5Lzs8ttYHTQo644EzI1gvD5edRycDHg")
    private String accessToken;

    @Schema(example = "e54a88f7-0410-237c-969d-5a33b43f57ad")
    private String refreshToken;

}
