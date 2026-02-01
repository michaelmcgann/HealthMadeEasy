package com.mike.healthmadeeasy.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Food {

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

    public Food( UUID id, String name,
                 BigDecimal caloriesPerGram,
                 BigDecimal proteinPerGram,
                 BigDecimal carbsPerGram,
                 BigDecimal fatPerGram) {

        this.id = id;
        this.name = name;
        this.caloriesPerGram = Objects.requireNonNull( caloriesPerGram, "caloriesPerGram" );
        this.proteinPerGram  = Objects.requireNonNull( proteinPerGram, "proteinPerGram" );
        this.carbsPerGram    = Objects.requireNonNull( carbsPerGram, "carbsPerGram" );
        this.fatPerGram      = Objects.requireNonNull( fatPerGram, "fatPerGram" );

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
