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

    @Column(name = "position", nullable = false)
    private int position;

}
