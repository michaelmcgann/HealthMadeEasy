package com.mike.healthmadeeasy.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "foods")
public class FoodEntity {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "protein_per_gram", nullable = false, precision = 10, scale = 7)
    private BigDecimal proteinPerGram;

    @Column(name = "carbs_per_gram", nullable = false, precision = 10, scale = 7)
    private BigDecimal carbsPerGram;

    @Column(name = "fat_per_gram", nullable = false, precision = 10, scale = 7)
    private BigDecimal fatPerGram;

    @Column(name = "calories_per_gram", nullable = false, precision = 10, scale = 7)
    private BigDecimal caloriesPerGram;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public FoodEntity() {}

    public FoodEntity(UUID id, String name, BigDecimal proteinPerGram, BigDecimal carbsPerGram,
                      BigDecimal fatPerGram, BigDecimal caloriesPerGram) {
        this.id = id;
        this.name = name;
        this.proteinPerGram = proteinPerGram;
        this.carbsPerGram = carbsPerGram;
        this.fatPerGram = fatPerGram;
        this.caloriesPerGram = caloriesPerGram;
    }

    //////////////////////////////////
    /// GETTERS & SETTERS
    //////////////////////////////////

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getProteinPerGram() {
        return proteinPerGram;
    }

    public void setProteinPerGram(BigDecimal proteinPerGram) {
        this.proteinPerGram = proteinPerGram;
    }

    public BigDecimal getCarbsPerGram() {
        return carbsPerGram;
    }

    public void setCarbsPerGram(BigDecimal carbsPerGram) {
        this.carbsPerGram = carbsPerGram;
    }

    public BigDecimal getFatPerGram() {
        return fatPerGram;
    }

    public void setFatPerGram(BigDecimal fatPerGram) {
        this.fatPerGram = fatPerGram;
    }

    public BigDecimal getCaloriesPerGram() {
        return caloriesPerGram;
    }

    public void setCaloriesPerGram(BigDecimal caloriesPerGram) {
        this.caloriesPerGram = caloriesPerGram;
    }

    //////////////////////////////////
    /// EQUALS & HASHCODE
    //////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodEntity other)) return false;
        return id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
