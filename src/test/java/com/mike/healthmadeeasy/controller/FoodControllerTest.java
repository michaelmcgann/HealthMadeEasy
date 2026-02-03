package com.mike.healthmadeeasy.controller;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.exception.FoodNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mockMvc.perform(post("/api/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/foods/" + id))
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

        mockMvc.perform(post("/api/foods").contentType(MediaType.APPLICATION_JSON).content(json))
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

        mockMvc.perform(get("/api/foods").accept(MediaType.APPLICATION_JSON))
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
    void getById_returns200_andFoodBody() throws Exception {

        UUID id = UUID.randomUUID();
        Food food = new Food(
                id,
                "Apple",
                new BigDecimal("0.8"),
                new BigDecimal("0.02"),
                new BigDecimal("0.20"),
                new BigDecimal("0.01")
        );

        when(foodService.get(id)).thenReturn(food);

        mockMvc.perform(get("/api/foods/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.caloriesPerGram").value(0.8))
                .andExpect(jsonPath("$.name").value("Apple"));

        verify(foodService, times(1)).get(id);
        verifyNoMoreInteractions(foodService);
    }

    @Test
    void getId_whenMissing_return404() throws Exception {

        UUID id = UUID.randomUUID();

        when(foodService.get(id)).thenThrow(FoodNotFoundException.class);

        mockMvc.perform(get("/api/foods/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Food Not Found"));

        verify(foodService, times(1)).get(id);
        verifyNoMoreInteractions(foodService);

    }

    @Test
    void getId__withInvalidUuid_returns400_andDoesNotCallService() throws Exception {

        mockMvc.perform(get("/api/foods/{id}", "Not a UUID").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(foodService);
    }

    @Test
    void delete_returns204_andCallsService() throws Exception {

        UUID id = UUID.randomUUID();

        doNothing().when(foodService).delete(id);

        mockMvc.perform(delete("/api/foods/{id}", id))
                .andExpect(status().isNoContent());

        verify(foodService, times(1)).delete(id);
        verifyNoMoreInteractions(foodService);
    }

    @Test
    void delete_whenMissing_returns404() throws Exception {

        UUID id = UUID.randomUUID();

        doThrow(new FoodNotFoundException(id.toString())).when(foodService).delete(id);

        mockMvc.perform(delete("/api/foods/{id}", id))
                .andExpect(status().isNotFound());

        verify(foodService, times(1)).delete(id);
        verifyNoMoreInteractions(foodService);
    }

    @Test
    void delete_withInvalidUuid_returns400_andDoesNotCallService() throws Exception {

        mockMvc.perform(delete("/api/foods/{id}", "Not a UUID"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(foodService);

    }

}

