package com.mike.healthmadeeasy.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class FoodCreateRequest {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @NotBlank
    @Size(max = 100)
    private String     name;

    @NotNull @Positive @Max(1000)
    private Integer    referenceGrams;

    @NotNull @PositiveOrZero @Digits(integer = 4, fraction = 2)
    private BigDecimal calories;

    @NotNull @PositiveOrZero @Digits(integer = 4, fraction = 2)
    private BigDecimal protein;

    @NotNull @PositiveOrZero @Digits(integer = 4, fraction = 2)
    private BigDecimal carbs;

    @NotNull @PositiveOrZero @Digits(integer = 4, fraction = 2)
    private BigDecimal fat;

    //////////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReferenceGrams() {
        return referenceGrams;
    }

    public void setReferenceGrams(Integer referenceGrams) {
        this.referenceGrams = referenceGrams;
    }

    public BigDecimal getCalories() {
        return calories;
    }

    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }
}
