package com.mike.healthmadeeasy.controller;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodController.class)
@Import(ApiExceptionHandler.class)
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodService foodService;

    @Test
    void create_returns201_locationHeader_andResponseBody() throws Exception {

        UUID id = UUID.randomUUID();

        Food food = new Food(
                id,
                "Apple",
                new BigDecimal("0.8"),
                new BigDecimal("0.02"),
                new BigDecimal("0.20"),
                new BigDecimal("0.01")
        );

        when(foodService.create(any())).thenReturn(food);

        String json = """
                {
                "name": "Apple",
                "referenceGrams": 100,
                "calories": 80.00,
                "protein": 2.00,
                "carbs": 20.00,
                "fat": 1.00
                }
                """;

        mockMvc.perform(post("/api/food")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/food/" + id))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.caloriesPerGram").value(0.8));

        verify(foodService, times(1)).create(any());
        verifyNoMoreInteractions(foodService);
    }

    @Test
    void create_withBlankName_returns400_andDoesNotCallService() throws Exception {

        String json = """
        {
          "name": "   ",
          "referenceGrams": 100,
          "calories": 80.00,
          "protein": 2.00,
          "carbs": 20.00,
          "fat": 1.00
        }
        """;

        mockMvc.perform(post("/api/food").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.errors.name").isArray());

        verifyNoInteractions(foodService);
    }

    @Test
    void getAll_returns200_andListOfFoods() throws Exception {

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Food food1 = new Food(
                id1,
                "Apple",
                new BigDecimal("0.8"),
                new BigDecimal("0.02"),
                new BigDecimal("0.20"),
                new BigDecimal("0.01")
        );

        Food food2 = new Food(
                id2,
                "Banana",
                new BigDecimal("0.9"),
                new BigDecimal("0.01"),
                new BigDecimal("0.23"),
                new BigDecimal("0.00")
        );

        when(foodService.list()).thenReturn(List.of(food1, food2));

        mockMvc.perform(get("/api/food").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].caloriesPerGram").value(0.8))
                .andExpect(jsonPath("$[1].id").value(id2.toString()))
                .andExpect(jsonPath("$[1].name").value("Banana"))
                .andExpect(jsonPath("$[1].carbsPerGram").value(0.23));

        verify(foodService, times((1))).list();
        verifyNoMoreInteractions(foodService);

    }

    @Test
    void get

}

