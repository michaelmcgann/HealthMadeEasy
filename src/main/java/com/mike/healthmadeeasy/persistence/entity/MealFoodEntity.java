package com.mike.healthmadeeasy.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_food")
public class MealFoodEntity {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @EmbeddedId
    private MealFoodId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mealId")
    @JoinColumn(name = "meal_id", nullable = false)
    private MealEntity meal;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealFoodEntity() {
    }

    //////////////////////////////////
    /// GETTERS & SETTERS
    //////////////////////////////////

    public MealFoodId getId() {
        return id;
    }

    public void setId(MealFoodId id) {
        this.id = id;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }

}
