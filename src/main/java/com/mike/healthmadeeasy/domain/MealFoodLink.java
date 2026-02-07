package com.mike.healthmadeeasy.domain;

import java.util.Objects;
import java.util.UUID;

public class MealFoodLink {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID foodId;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealFoodLink(UUID foodId) {
        this.foodId = Objects.requireNonNull(foodId, "foodId");
    }

    //////////////////////////////////
    /// GETTERS (IMMUTABLE)
    //////////////////////////////////

    public UUID getFoodId() {
        return foodId;
    }

}
