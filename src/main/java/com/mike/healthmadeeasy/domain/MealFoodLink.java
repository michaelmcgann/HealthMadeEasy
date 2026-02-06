package com.mike.healthmadeeasy.domain;

import java.util.Objects;
import java.util.UUID;

public class MealFoodLink {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID foodId;
    private final int position;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealFoodLink(UUID foodId, int position) {
        this.foodId = Objects.requireNonNull(foodId, "foodId");
        this.position = position;
    }

    //////////////////////////////////
    /// GETTERS (IMMUTABLE)
    //////////////////////////////////

    public UUID getFoodId() {
        return foodId;
    }

    public int getPosition() {
        return position;
    }
}
