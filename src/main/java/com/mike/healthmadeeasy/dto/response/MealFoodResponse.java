package com.mike.healthmadeeasy.dto.response;

import java.util.UUID;

public class MealFoodResponse {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID foodID;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealFoodResponse(UUID foodID) {
        this.foodID = foodID;
    }

    //////////////////////////////////
    /// GETTERS (IMMUTABLE)
    //////////////////////////////////

    public UUID getFoodID() {
        return foodID;
    }
}
