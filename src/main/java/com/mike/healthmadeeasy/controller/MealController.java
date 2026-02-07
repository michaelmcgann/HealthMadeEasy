package com.mike.healthmadeeasy.controller;

import com.mike.healthmadeeasy.domain.Meal;
import com.mike.healthmadeeasy.domain.MealFoodLink;
import com.mike.healthmadeeasy.dto.request.MealCreateRequest;
import com.mike.healthmadeeasy.dto.response.MealFoodResponse;
import com.mike.healthmadeeasy.dto.response.MealResponse;
import com.mike.healthmadeeasy.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final MealService mealService;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    //////////////////////////////////
    /// PUBLIC API
    //////////////////////////////////

    @PostMapping
    public ResponseEntity<MealResponse> create(@Valid @RequestBody MealCreateRequest request) {

        Meal createdMeal = mealService.create(request);
        MealResponse response = toResponse(createdMeal);
        URI location = URI.create("/api/meals/" + createdMeal.getId());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MealResponse>> getAll() {
        List<MealResponse> responses = mealService.list().stream()
                .map(MealController::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getById(@PathVariable UUID id) {
        Meal meal = mealService.get(id);
        MealResponse mealResponse = MealController.toResponse(meal);
        return ResponseEntity.ok(mealResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        mealService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //////////////////////////////////
    /// HELPER METHODS
    //////////////////////////////////

    private static MealResponse toResponse(Meal meal) {
        List<MealFoodResponse> foods = meal.getFoods().stream()
                .map(MealController::toFoodResponse)
                .toList();

        return new MealResponse(meal.getId(), meal.getName(), foods);
    }

    private static MealFoodResponse toFoodResponse(MealFoodLink link) {
        return new MealFoodResponse(link.getFoodId());
    }


}
