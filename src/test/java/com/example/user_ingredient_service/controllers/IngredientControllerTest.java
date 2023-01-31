package com.example.user_ingredient_service.controllers;

import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import com.example.user_ingredient_service.mock.MockKafka;
import com.example.user_ingredient_service.service.IngredientService;
import com.example.user_ingredient_service.test_container.Neo4jConfiguredContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.example.user_ingredient_service.generator.IngredientDtoGeneratorUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.profiles.active = test",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"})
@AutoConfigureMockMvc
@Transactional
@ExtendWith(Neo4jConfiguredContainer.class)
class IngredientControllerTest extends MockKafka {

    @Autowired
    private IngredientService ingredientService;

    private final String userDetailsNameAdmin = "admin_1";

    private final String userDetailsNameUser = "user_1";

    private Long validIngredientId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        Page<IngredientResponseDto> allIngredients = ingredientService.getAllIngredients(PageRequest.of(0,20));
        List<IngredientResponseDto> content = allIngredients.getContent();
        validIngredientId = content.get(0).getId();
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldCreateIngredient() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createValidIngredientCreateDto())))
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(userDetailsNameUser)
    void testCreateIngredientShouldThrowCauseForbiddenForUserRole() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createValidIngredientCreateDto())))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreateIngredientShouldThrowCauseUserAnonymous() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createValidIngredientCreateDto())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseInvalidIngredientPrice() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createIngredientWithInvalidPrice())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testGetAllIngredientsShouldReturnAllIngredients() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient")
                        .content(objectToJson(Pageable.unpaged())))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testGetIngredientByIdShouldReturnIngredient() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/{id}",validIngredientId)).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testGetIngredientByIdShouldThrowCauseIngredientNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/{id}",2000)).andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseOfNotExistingIngredients() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createRecipeIngredientDtoWithNotExistingIngredients())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseOfInvalidBasicLossProbabilityAndPrice() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createBasicIngredientWithInvalidPriceAndLossProbability())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseOfInvalidLossProbability() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createIngredientWithInvalidLossProbability())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseOfInvalidIngredientsCreatedFromSize() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createIngredientWithIngredientsSizeEqualOne())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseOfAlreadyExistingIngredientName() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createIngredientWithAlreadyExistingName("Earth"))))
                .andExpect(status().isBadRequest());
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
