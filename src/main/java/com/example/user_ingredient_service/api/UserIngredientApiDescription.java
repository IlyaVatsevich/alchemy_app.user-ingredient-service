package com.example.user_ingredient_service.api;


import com.example.user_ingredient_service.dto.UserIngredientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user_ingredient")
@Tag(description = "To interact with user bag",name = "User Ingredients")
public interface UserIngredientApiDescription {

    @Operation(summary = "Get user bag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<Page<UserIngredientDto>> getUserIngredients(@ParameterObject Pageable pageable);

}
