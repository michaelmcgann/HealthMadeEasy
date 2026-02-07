package com.mike.healthmadeeasy.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class MealCreateRequest {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotEmpty
    @Size(max = 100)
    @Valid
    private List<MealFoodRequest> foods;

    //////////////////////////////////
    /// GETTERS & SETTERS
    //////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MealFoodRequest> getFoods() {
        return foods;
    }

    public void setFoods(List<MealFoodRequest> foods) {
        this.foods = foods;
    }
}
