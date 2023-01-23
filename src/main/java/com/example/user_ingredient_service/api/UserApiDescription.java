package com.example.user_ingredient_service.api;

import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.dto.TokenRequest;
import com.example.user_ingredient_service.dto.TokenResponse;
import com.example.user_ingredient_service.dto.UserLogDto;
import com.example.user_ingredient_service.dto.UserRegDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
@Tag(description = "To interact with users",name = "Users")
public interface UserApiDescription {

    @Operation(summary = "Registration for new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRegDto.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "user successfully registered"),
            @ApiResponse(responseCode = "400",description = "Invalid credentials from client"),
            @ApiResponse(responseCode = "403",description = "Logged user don't have access for this"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping("/registration")
    ResponseEntity<TokenResponse> registration(@RequestBody UserRegDto userRegDto);

    @Operation(summary = "Get high score table by gold")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @GetMapping("/high_score/gold")
    ResponseEntity<Page<HighScoreTable>> getUsersByMaxGold(@ParameterObject Pageable pageable);

    @Operation(summary = "Login into system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            schema =@Schema(implementation = UserLogDto.class,requiredMode = Schema.RequiredMode.REQUIRED))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400",description = "Invalid credentials from user"),
            @ApiResponse(responseCode = "403",description = "Logged user don't have access for this"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping("/login")
    ResponseEntity<TokenResponse> login(@RequestBody UserLogDto userLogDto);

    @Operation(summary = "Get high score table by ingredient count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @GetMapping("/high_score/count")
    ResponseEntity<Page<HighScoreTable>> getUsersByMaxCount(@ParameterObject Pageable pageable);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "403",description = "Logged user don't have access for this"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping("/refresh_token")
    ResponseEntity<TokenResponse> getNewAccessAndRefreshTokenByRefreshToken(@RequestBody TokenRequest tokenRequest);
}
