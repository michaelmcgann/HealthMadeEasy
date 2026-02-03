package com.mike.healthmadeeasy.controller;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.dto.request.FoodCreateRequest;
import com.mike.healthmadeeasy.dto.response.FoodResponse;
import com.mike.healthmadeeasy.service.FoodService;
import com.mike.healthmadeeasy.service.FoodServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    // REMEMBER @Valid

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final FoodService foodService;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    //////////////////////////////////
    /// END POINTS
    //////////////////////////////////

    @PostMapping
    public ResponseEntity<FoodResponse> create(@Valid @RequestBody FoodCreateRequest request) {
        Food createdFood = foodService.create(request);
        FoodResponse response = toResponse(createdFood);
        URI location = URI.create("/api/food/" + createdFood.getId());
        return ResponseEntity.created(location).body(response);
    }

    //////////////////////////////////
    /// HELPER METHODS
    //////////////////////////////////

    private static FoodResponse toResponse(Food food) {
        return new FoodResponse(
                food.getId(),
                food.getName(),
                food.getCaloriesPerGram(),
                food.getProteinPerGram(),
                food.getCarbsPerGram(),
                food.getFatPerGram()
        );
    }


}
