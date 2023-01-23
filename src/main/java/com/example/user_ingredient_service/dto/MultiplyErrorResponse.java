package com.example.user_ingredient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class MultiplyErrorResponse {

    private List<Map<String,String>> messages;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;

}
