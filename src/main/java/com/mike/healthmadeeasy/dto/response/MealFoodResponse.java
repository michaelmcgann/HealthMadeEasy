package com.mike.healthmadeeasy.dto.response;

import java.util.UUID;

public class MealFoodResponse {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID foodId;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealFoodResponse(UUID foodID) {
        this.foodId = foodID;
    }

    //////////////////////////////////
    /// GETTERS (IMMUTABLE)
    //////////////////////////////////

    public UUID getFoodId() {
        return foodId;
    }
}
