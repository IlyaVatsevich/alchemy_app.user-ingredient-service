package com.example.user_ingredient_service.api;

import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;
import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/ingredient")
@Tag(name = "Ingredient",description = "To interact with ingredients")
public interface IngredientApiDescription {

    @Operation(summary = "Create new ingredient",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = IngredientCreateDto.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "New ingredient created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientCreateDto.class,requiredMode = Schema.RequiredMode.REQUIRED))}),
            @ApiResponse(responseCode = "400",description = "Invalid ingredient data"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "403",description = "Client does not have access to this"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<IngredientCreateDto> createIngredient(@RequestBody IngredientCreateDto ingredientDto);

    @Operation(summary = "Get all ingredients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "403",description = "Client does not have access to this"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<Page<IngredientResponseDto>> getAllIngredients(@ParameterObject Pageable pageable);

    @Operation(summary = "Get ingredient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = IngredientResponseDto.class))}),
            @ApiResponse(responseCode = "400",description = "Ingredient with such id not exist"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "403",description = "Client does not have access to this"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @GetMapping("/{id}")
    ResponseEntity<IngredientResponseDto> getIngredient(@Parameter(description = "ingredient id",required = true,example = "1000")
                                                        @PathVariable Long id);

}
