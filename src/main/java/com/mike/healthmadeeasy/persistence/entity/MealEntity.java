package com.mike.healthmadeeasy.persistence.entity;

import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<MealFoodEntity> foods;


}
