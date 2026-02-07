package com.mike.healthmadeeasy.dto.response;

import java.util.List;
import java.util.UUID;

public class MealResponse {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID id;
    private final String name;
    private final List<MealFoodResponse> foods;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealResponse(UUID id, String name, List<MealFoodResponse> foods) {
        this.id = id;
        this.name = name;
        this.foods = foods;
    }

    //////////////////////////////////
    /// GETTERS (IMMUTABLE)
    //////////////////////////////////

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MealFoodResponse> getFoods() {
        return foods;
    }
}
