package com.mike.healthmadeeasy.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MealFoodId implements Serializable {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @Column(name = "meal_id", nullable = false)
    private UUID mealId;

    @Column(name = "food_id", nullable = false)
    private UUID foodId;

    //////////////////////////////////
    /// GETTERS & SETTERS
    //////////////////////////////////

    public UUID getMealId() {
        return mealId;
    }

    public void setMealId(UUID mealId) {
        this.mealId = mealId;
    }

    public UUID getFoodId() {
        return foodId;
    }

    public void setFoodId(UUID foodId) {
        this.foodId = foodId;
    }

    //////////////////////////////////
    /// EQUALS & HASH
    //////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MealFoodId that = (MealFoodId) o;
        return Objects.equals(mealId, that.mealId) && Objects.equals(foodId, that.foodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, foodId);
    }
}
