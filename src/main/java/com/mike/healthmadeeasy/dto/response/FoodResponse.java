package com.mike.healthmadeeasy.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class FoodResponse {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final UUID       id;
    private final String     name;
    private final BigDecimal caloriesPerGram;
    private final BigDecimal proteinPerGram;
    private final BigDecimal carbsPerGram;
    private final BigDecimal fatPerGram;


    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public FoodResponse(UUID id, String name,
                        BigDecimal caloriesPerGram,
                        BigDecimal proteinPerGram,
                        BigDecimal carbsPerGram,
                        BigDecimal fatPerGram) {

        this.id = id;
        this.name = name;
        this.caloriesPerGram = caloriesPerGram;
        this.proteinPerGram = proteinPerGram;
        this.carbsPerGram = carbsPerGram;
        this.fatPerGram = fatPerGram;
    }

    //////////////////////////////////
    /// GETTERS (IMMUTABLE)
    //////////////////////////////////

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getCaloriesPerGram() {
        return caloriesPerGram;
    }

    public BigDecimal getProteinPerGram() {
        return proteinPerGram;
    }

    public BigDecimal getCarbsPerGram() {
        return carbsPerGram;
    }

    public BigDecimal getFatPerGram() {
        return fatPerGram;
    }

}
