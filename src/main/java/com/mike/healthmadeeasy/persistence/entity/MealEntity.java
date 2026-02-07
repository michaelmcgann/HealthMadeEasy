package com.mike.healthmadeeasy.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meals")
public class MealEntity {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    @Id @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MealFoodEntity> foods = new ArrayList<>();

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealEntity() {
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

    public List<MealFoodEntity> getFoods() {
        return foods;
    }

    public void setFoods(List<MealFoodEntity> foods) {
        this.foods = foods;
    }
}
