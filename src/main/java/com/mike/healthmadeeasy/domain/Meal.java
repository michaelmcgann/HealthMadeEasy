package com.mike.healthmadeeasy.domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Meal {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID id;
    private final String name;
    private final List<MealFoodLink> foods;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public Meal(UUID id, String name, List<MealFoodLink> foods) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.foods = List.copyOf(Objects.requireNonNull(foods, "foods"));
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

    public List<MealFoodLink> getFoods() {
        return foods;
    }
}
