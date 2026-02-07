package com.mike.healthmadeeasy.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class MealFoodRequest {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @NotNull
    private UUID foodId;

    //////////////////////////////////
    /// GETTERS & SETTERS
    //////////////////////////////////

    public UUID getFoodId() {
        return foodId;
    }

    public void setFoodId(UUID foodId) {
        this.foodId = foodId;
    }
}
