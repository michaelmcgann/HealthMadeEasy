package com.mike.healthmadeeasy.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
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

}
